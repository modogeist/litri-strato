package com.litri.strato.csm.controller;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.configuration.TestServiceConfig;
import com.litri.strato.csm.helper.TestDataHelper;
import com.litri.strato.csm.helper.sync.TestSyncDataHelper;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.csm.service.sync.ProviderService;
import com.litri.strato.csm.service.sync.ServiceProviderLinkService;
import com.litri.strato.csm.service.sync.ServiceService;
import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.dto.SchemaEntry;
import com.litri.strato.dsm.sync.DataSchema;
import com.litri.strato.dsm.sync.ProviderDetail;
import com.litri.strato.dsm.sync.ProviderSchema;
import com.litri.strato.dsm.sync.ServiceDetail;
import com.litri.strato.dsm.sync.ServiceProviderLink;
import com.litri.strato.dsm.sync.SyncSchema;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TestRegistryConfig.class, TestServiceConfig.class})
public class ForwardControllerTest {
	
	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ServiceService serviceService;
	
	@Autowired
	ProviderService providerService;
	
	@Autowired
	ServiceProviderLinkService serviceProviderLinkService;
	
	@Autowired
	SchemaService<ProviderSchema> providerSchemaService;
	
	@Autowired
	SchemaService<DataSchema> dataSchemaService;
	
	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.serviceService);
		Mockito.reset(this.providerService);
		Mockito.reset(this.serviceProviderLinkService);
		Mockito.reset(this.providerSchemaService);
		
		this.dataSchemaService.removeAll();
	}
	
	@Test
	public void forward_GetDataSchemas_ReturnEmptyList() {
		try {
			// Setup
			Info<ServiceDetail> serviceInfo = this.getServiceInfo(UUID.randomUUID());
			Info<ProviderDetail> providerInfo = this.getProviderInfo(UUID.randomUUID());
			Info<ServiceProviderLink> serviceProviderLinkInfo = this.getServiceProviderLinkInfo(serviceInfo.getId(), providerInfo.getId());
			ProviderSchema providerSchema = this.getProviderSchema("/dataschemas");

			Mockito
					.doReturn(serviceInfo)
					.when(this.serviceService).get(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerInfo)
					.when(this.providerService).get(Mockito.eq(providerInfo.getId()));
			Mockito
					.doReturn(List.of(serviceProviderLinkInfo))
					.when(this.serviceProviderLinkService).getByServiceInfoId(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerSchema)
					.when(this.providerSchemaService).get(Mockito.eq(providerInfo.getDetail().getProviderSchemaId()));

			// Execute
			String url = "/forwards/" + serviceInfo.getId().toString() + "?key1=value1";
			ResponseEntity<List> response = this.restTemplate
					.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List>() {});

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			List actuals = response.getBody();
			Assert.assertNotNull(actuals);
			Assert.assertTrue(actuals.isEmpty());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void forward_GetDataSchemas_ReturnNonemptyList() {
		try {
			// Setup
			Info<ServiceDetail> serviceInfo = this.getServiceInfo(UUID.randomUUID());
			Info<ProviderDetail> providerInfo = this.getProviderInfo(UUID.randomUUID());
			Info<ServiceProviderLink> serviceProviderLinkInfo = this.getServiceProviderLinkInfo(serviceInfo.getId(), providerInfo.getId());
			ProviderSchema providerSchema = this.getProviderSchema("/dataschemas");

			Mockito
					.doReturn(serviceInfo)
					.when(this.serviceService).get(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerInfo)
					.when(this.providerService).get(Mockito.eq(providerInfo.getId()));
			Mockito
					.doReturn(List.of(serviceProviderLinkInfo))
					.when(this.serviceProviderLinkService).getByServiceInfoId(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerSchema)
					.when(this.providerSchemaService).get(Mockito.eq(providerInfo.getDetail().getProviderSchemaId()));

			SchemaId schemaId = TestDataHelper.getMinSchemaId("data", "1");
			DataSchema dataSchema = TestSyncDataHelper.getMinDataSchema();

			this.dataSchemaService.put(schemaId, dataSchema);

			// Execute
			String query = String.format("?groupId=%s&artifactId=%s&version=%s", schemaId.getGroupId(), schemaId.getArtifactId(), schemaId.getVersion());
			String url = "/forwards/" + serviceInfo.getId().toString() + query;
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.ACCEPT_ENCODING, MediaType.APPLICATION_JSON_VALUE);
			ResponseEntity<List> response = this.restTemplate
					.exchange(url, HttpMethod.GET, new HttpEntity(headers), new ParameterizedTypeReference<List>() {});

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			List actuals = response.getBody();
			Assert.assertNotNull(actuals);
			Assert.assertFalse(actuals.isEmpty());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void forward_PostDataSchemas_ReturnSuccess() {
		try {
			// Setup
			Info<ServiceDetail> serviceInfo = this.getServiceInfo(UUID.randomUUID());
			Info<ProviderDetail> providerInfo = this.getProviderInfo(UUID.randomUUID());
			Info<ServiceProviderLink> serviceProviderLinkInfo = this.getServiceProviderLinkInfo(serviceInfo.getId(), providerInfo.getId());
			ProviderSchema providerSchema = this.getProviderSchema("/dataschemas");

			Mockito
					.doReturn(serviceInfo)
					.when(this.serviceService).get(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerInfo)
					.when(this.providerService).get(Mockito.eq(providerInfo.getId()));
			Mockito
					.doReturn(List.of(serviceProviderLinkInfo))
					.when(this.serviceProviderLinkService).getByServiceInfoId(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerSchema)
					.when(this.providerSchemaService).get(Mockito.eq(providerInfo.getDetail().getProviderSchemaId()));

			SchemaId schemaId = TestDataHelper.getMinSchemaId("data", "1");
			DataSchema dataSchema = TestSyncDataHelper.getMinDataSchema();
			SchemaEntry schemaEntry = SchemaEntry.fromObject(schemaId, dataSchema);

			// Execute
			String url = "/forwards/" + serviceInfo.getId().toString() + "?key1=value1";
			ResponseEntity response = this.restTemplate
					.exchange(url, HttpMethod.POST, new HttpEntity(schemaEntry), Void.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNull(response.getBody());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void forward_PutDataSchemas_ReturnSuccess() {
		try {
			// Setup
			Info<ServiceDetail> serviceInfo = this.getServiceInfo(UUID.randomUUID());
			Info<ProviderDetail> providerInfo = this.getProviderInfo(UUID.randomUUID());
			Info<ServiceProviderLink> serviceProviderLinkInfo = this.getServiceProviderLinkInfo(serviceInfo.getId(), providerInfo.getId());
			ProviderSchema providerSchema = this.getProviderSchema("/dataschemas");

			Mockito
					.doReturn(serviceInfo)
					.when(this.serviceService).get(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerInfo)
					.when(this.providerService).get(Mockito.eq(providerInfo.getId()));
			Mockito
					.doReturn(List.of(serviceProviderLinkInfo))
					.when(this.serviceProviderLinkService).getByServiceInfoId(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerSchema)
					.when(this.providerSchemaService).get(Mockito.eq(providerInfo.getDetail().getProviderSchemaId()));

			SchemaId schemaId = TestDataHelper.getMinSchemaId("data", "1");
			DataSchema dataSchema = TestSyncDataHelper.getMinDataSchema();
			SchemaEntry schemaEntry = SchemaEntry.fromObject(schemaId, dataSchema);
			
			// Execute
			String url = "/forwards/" + serviceInfo.getId().toString() + "?key1=value1";
			ResponseEntity response = this.restTemplate
					.exchange(url, HttpMethod.PUT, new HttpEntity(schemaEntry), Void.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNull(response.getBody());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void forward_DeleteDataSchemas_ReturnSuccess() {
		try {
			// Setup
			Info<ServiceDetail> serviceInfo = this.getServiceInfo(UUID.randomUUID());
			Info<ProviderDetail> providerInfo = this.getProviderInfo(UUID.randomUUID());
			Info<ServiceProviderLink> serviceProviderLinkInfo = this.getServiceProviderLinkInfo(serviceInfo.getId(), providerInfo.getId());
			ProviderSchema providerSchema = this.getProviderSchema("/dataschemas");

			Mockito
					.doReturn(serviceInfo)
					.when(this.serviceService).get(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerInfo)
					.when(this.providerService).get(Mockito.eq(providerInfo.getId()));
			Mockito
					.doReturn(List.of(serviceProviderLinkInfo))
					.when(this.serviceProviderLinkService).getByServiceInfoId(Mockito.eq(serviceInfo.getId()));
			Mockito
					.doReturn(providerSchema)
					.when(this.providerSchemaService).get(Mockito.eq(providerInfo.getDetail().getProviderSchemaId()));

			SchemaId schemaId = TestDataHelper.getMinSchemaId("data", "1");

			// Execute
			String url = "/forwards/" + serviceInfo.getId().toString() + "?key1=value1";
			ResponseEntity response = this.restTemplate
					.exchange(url, HttpMethod.DELETE, new HttpEntity(schemaId), Void.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNull(response.getBody());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	private Info<ServiceDetail> getServiceInfo(UUID serviceInfoId) {
		return Info.<ServiceDetail>builder()
				.id(serviceInfoId)
				.build();
	}
	
	private Info<ServiceProviderLink> getServiceProviderLinkInfo(UUID serviceInfoId, UUID providerInfoId) {
		return Info.<ServiceProviderLink>builder()
				.id(UUID.randomUUID())
				.detail(ServiceProviderLink.builder()
						.serviceInfoId(serviceInfoId)
						.providerInfoId(providerInfoId)
						.build())
				.build();
	}
	
	private Info<ProviderDetail> getProviderInfo(UUID providerInfoId) {
		return Info.<ProviderDetail>builder()
				.id(providerInfoId)
				.detail(ProviderDetail.builder()
						.serviceSchemaId(TestDataHelper.getMinSchemaId("service", "1"))
						.providerSchemaId(TestDataHelper.getMinSchemaId("provider", "1"))
						.instanceId(UUID.randomUUID())
						.serverIndex(0)
						.build())
				.build();
	}
	
	private ProviderSchema getProviderSchema(String path) {
		ProviderSchema providerSchema = TestSyncDataHelper.getMinProviderSchema();
		providerSchema.setServers(List.of(SyncSchema.Server.builder()
				.url(this.restTemplate.getRootUri() + path)
				.build()));
		return providerSchema;
	}
	
}
