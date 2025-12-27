package com.litri.strato.csm.controller;

import com.litri.strato.csm.helper.TestDataHelper;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.dto.SchemaEntry;
import java.util.List;
import java.util.function.Supplier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class SchemaControllerTest<T> {
	
	protected String url;
	protected TestRestTemplate restTemplate;
	protected Class<T> schemaType;
	protected ParameterizedTypeReference<List<T>> listSchemaTypeReference;
	protected Supplier<T> schemaSupplier;
	protected SchemaService<T> schemaService;
	
	@Before
	public void setUp() {
		Mockito.reset(this.schemaService);
	}
	
	@Test
	public void getSchemas_NoId_ReturnSchemas() {
		try {
			// Setup
			T schema = this.schemaSupplier.get();
			Mockito
					.when(this.schemaService.get(Mockito.any(), Mockito.any(), Mockito.any()))
					.thenReturn(List.of(schema));

			// Execute
			ResponseEntity<List<T>> response = this.restTemplate
					.exchange(this.url, HttpMethod.GET, HttpEntity.EMPTY, this.listSchemaTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			List<T> actuals = response.getBody();
			Assert.assertNotNull(actuals);
			Assert.assertFalse(actuals.isEmpty());
			Assert.assertTrue(actuals.contains(schema));

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void getSchemas_SpecifiedId_ReturnSchemas() {
		try {
			// Setup
			T schema = this.schemaSupplier.get();
			Mockito
					.when(this.schemaService.get(Mockito.any(), Mockito.any(), Mockito.any()))
					.thenReturn(List.of(schema));

			// Execute
			String url = this.url + "?groupId=groupId-test&artifactId=artifactId-test&version=version-test";
			ResponseEntity<List<T>> response = this.restTemplate
					.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, this.listSchemaTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			List<T> actuals = response.getBody();
			Assert.assertNotNull(actuals);
			Assert.assertFalse(actuals.isEmpty());
			Assert.assertTrue(actuals.contains(schema));

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void addSchema_SpecifiedSchema_Success() {
		try {
			// Setup
			T schema = this.schemaSupplier.get();
			SchemaEntry<T> schemaEntry = SchemaEntry.fromObject(TestDataHelper.getMinSchemaId("some", "1"), this.schemaSupplier.get());

			// Execute
			ResponseEntity response = this.restTemplate
					.exchange(this.url, HttpMethod.POST, new HttpEntity(schemaEntry), Void.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNull(response.getBody());

			// Verify
			Mockito.verify(this.schemaService, Mockito.times(1)).put(ArgumentMatchers.eq(schemaEntry.getSchemaId()), ArgumentMatchers.eq(schemaEntry.getSchema(this.schemaType)));
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void updateSchema_SpecifiedSchema_Success() {
		try {
			// Setup
			SchemaEntry<T> schemaEntry = SchemaEntry.fromObject(TestDataHelper.getMinSchemaId("some", "1"), this.schemaSupplier.get());

			// Execute
			ResponseEntity response = this.restTemplate
					.exchange(this.url, HttpMethod.PUT, new HttpEntity(schemaEntry), Void.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNull(response.getBody());

			// Verify
			Mockito.verify(this.schemaService, Mockito.times(1)).put(ArgumentMatchers.eq(schemaEntry.getSchemaId()), ArgumentMatchers.eq(schemaEntry.getSchema(this.schemaType)));
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void deleteSchema_SpecifiedSchema_Success() {
		try {
			// Setup
			SchemaId schemaId = TestDataHelper.getMinSchemaId("some", "1");

			// Execute
			ResponseEntity response = this.restTemplate
					.exchange(this.url, HttpMethod.DELETE, new HttpEntity(schemaId), Void.class);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			Assert.assertNull(response.getBody());

			// Verify
			Mockito.verify(this.schemaService, Mockito.times(1)).remove(ArgumentMatchers.eq(schemaId));
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
}
