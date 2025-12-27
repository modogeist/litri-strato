package com.litri.strato.csm.controller;

import com.litri.strato.csm.configuration.TestJobConfig;
import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.helper.TestDataHelper;
import com.litri.strato.csm.helper.sync.TestSyncDataHelper;
import com.litri.strato.csm.service.JobService;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.dto.SchemaEntry;
import com.litri.strato.dsm.sync.DataSchema;
import java.util.List;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TestRegistryConfig.class, TestJobConfig.class})
public class JobControllerTest {
	
	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	JobService jobService;
	
	@Autowired
	SchemaService<DataSchema> dataSchemaService;
	
	@Before
	public void setUp() throws Exception {
		Mockito.reset(this.jobService);
		
		this.dataSchemaService.removeAll();
	}
	
	@Test
	public void handle_GetDataSchemas_ReturnEmptyList() {
		try {
			// Setup
			// Execute
			ResponseEntity<Info<List>> response = this.restTemplate
					.exchange("/jobs/dataschemas", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Info<List>>() {});

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

			Info<List> actual = response.getBody();
			Assert.assertNotNull(actual);
			Assert.assertNotNull(actual.getId());
			Assert.assertNotNull(actual.getDetail());
			Assert.assertNotNull(actual.getInitialDate());
			Assert.assertNotNull(actual.getLastDate());
			Assert.assertNotNull(actual.getFinalDate());

			List actuals = actual.getDetail();
			Assert.assertNotNull(actuals);
			Assert.assertTrue(actuals.isEmpty());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void handle_PostDataSchema_ReturnNothing() {
		try {
			// Setup
			Info info = Info.builder()
					.detail(SchemaEntry.fromObject(TestDataHelper.getMinSchemaId("data", "1"), TestSyncDataHelper.getMinDataSchema()))
					.build();

			// Execute
			ResponseEntity<Info> response = this.restTemplate
					.exchange("/jobs/dataschemas", HttpMethod.POST, new HttpEntity(info), Info.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

			Info actual = response.getBody();
			Assert.assertNotNull(actual);
			Assert.assertNotNull(actual.getId());
			Assert.assertNull(actual.getDetail());
			Assert.assertNotNull(actual.getInitialDate());
			Assert.assertNotNull(actual.getLastDate());
			Assert.assertNotNull(actual.getFinalDate());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void handle_PutDataSchema_ReturnNothing() {
		try {
			// Setup
			Info info = Info.builder()
					.detail(SchemaEntry.fromObject(TestDataHelper.getMinSchemaId("data", "1"), TestSyncDataHelper.getMinDataSchema()))
					.build();

			// Execute
			ResponseEntity<Info> response = this.restTemplate
					.exchange("/jobs/dataschemas", HttpMethod.PUT, new HttpEntity(info), Info.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

			Info actual = response.getBody();
			Assert.assertNotNull(actual);
			Assert.assertNotNull(actual.getId());
			Assert.assertNull(actual.getDetail());
			Assert.assertNotNull(actual.getInitialDate());
			Assert.assertNotNull(actual.getLastDate());
			Assert.assertNotNull(actual.getFinalDate());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void handle_DeleteDataSchema_ReturnNothing() {
		try {
			// Setup
			Info info = Info.builder()
					.detail(TestDataHelper.getMinSchemaId("data", "1"))
					.build();

			// Execute
			ResponseEntity<Info> response = this.restTemplate
					.exchange("/jobs/dataschemas", HttpMethod.DELETE, new HttpEntity(info), Info.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

			Info actual = response.getBody();
			Assert.assertNotNull(actual);
			Assert.assertNotNull(actual.getId());
			Assert.assertNull(actual.getDetail());
			Assert.assertNotNull(actual.getInitialDate());
			Assert.assertNotNull(actual.getLastDate());
			Assert.assertNotNull(actual.getFinalDate());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
}
