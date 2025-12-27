package com.litri.strato.csm.controller;

import com.litri.strato.dsm.Info;
import com.litri.strato.csm.service.InfoService;
import java.util.List;
import java.util.function.Supplier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class InfoControllerTest<T> {
	
	protected String url;
	protected TestRestTemplate restTemplate;
	protected Class<T> detailType;
	protected ParameterizedTypeReference<Info<T>> infoTypeReference;
	protected ParameterizedTypeReference<List<Info<T>>> listInfoTypeReference;
	protected Supplier<T> detailSupplier;
	protected InfoService<T> infoService;
	
	@Before
	public void setUp() {
		this.infoService.removeAll();
	}
	
	@Test
	public void getInfos_EmptyInfo_ReturnNoInfos() {
		try {
			// Setup

			// Execute
			ResponseEntity<List<Info<T>>> response = this.restTemplate
					.exchange(this.url, HttpMethod.GET, HttpEntity.EMPTY, this.listInfoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			List<Info<T>> actualInfos = response.getBody();
			Assert.assertNotNull(actualInfos);
			Assert.assertTrue(actualInfos.isEmpty());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}

	@Test
	public void getInfos_SingleInfo_ReturnInfos() {
		try {
			// Setup
			T expectedDetail = this.detailSupplier.get();
			this.infoService.init(expectedDetail);

			// Execute
			ResponseEntity<List<Info<T>>> response = this.restTemplate
					.exchange(this.url, HttpMethod.GET, HttpEntity.EMPTY, this.listInfoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			List<Info<T>> actualInfos = response.getBody();
			Assert.assertNotNull(actualInfos);
			Assert.assertFalse(actualInfos.isEmpty());
			Assert.assertEquals(1, actualInfos.size());
			Assert.assertEquals(expectedDetail, actualInfos.get(0).getDetail());
			Assert.assertNotNull(actualInfos.get(0).getInitialDate());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}

	@Test
	public void getInfo_SpecifiedInfoId_ReturnInfo() {
		try {
			// Setup
			Info<T> expectedInfo = this.infoService.init(this.detailSupplier.get());

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url + "/" + expectedInfo.getId(), HttpMethod.GET, HttpEntity.EMPTY, this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			Info<T> actualInfo = response.getBody();
			Assert.assertNotNull(actualInfo);
			Assert.assertEquals(expectedInfo.getDetail(), actualInfo.getDetail());
			Assert.assertNotNull(actualInfo.getInitialDate());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}

	@Test
	public void getInfo_BadInfoId_ReturnErrorCode() {
		try {
			// Setup

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url + "/123", HttpMethod.GET, HttpEntity.EMPTY, this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void addInfo_SingleDetail_ReturnInfo() {
		try {
			// Setup
			T expectedDetail = this.detailSupplier.get();

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url, HttpMethod.POST, new HttpEntity(expectedDetail), this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			Info<T> actualInfo = response.getBody();
			Assert.assertNotNull(actualInfo);
			Assert.assertEquals(expectedDetail, actualInfo.getDetail());
			Assert.assertNotNull(actualInfo.getInitialDate());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}

	@Test
	public void addInfo_MissingDetail_ReturnErrorCode() {
		try {
			// Setup

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url, HttpMethod.POST, HttpEntity.EMPTY, this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}

	@Test
	public void updateInfo_SingleInfo_ReturnInfo() {
		try {
			// Setup
			Info<T> expectedInfo = this.infoService.init(this.detailSupplier.get());

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url, HttpMethod.PUT, new HttpEntity(expectedInfo), this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			Info<T> actualInfo = response.getBody();
			Assert.assertNotNull(actualInfo);
			Assert.assertEquals(expectedInfo, actualInfo);

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}

	@Test
	public void updateInfo_MissingDetail_ReturnErrorCode() {
		try {
			// Setup

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url, HttpMethod.PUT, HttpEntity.EMPTY, this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}

	@Test
	public void deleteInfo_SpecifiedInfoId_ReturnInfo() {
		try {
			// Setup
			Info<T> expectedInfo = this.infoService.init(this.detailSupplier.get());

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url + "/" + expectedInfo.getId(), HttpMethod.DELETE, HttpEntity.EMPTY, this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
			
			Info<T> actualInfo = response.getBody();
			Assert.assertNotNull(actualInfo);
			Assert.assertEquals(expectedInfo.getDetail(), actualInfo.getDetail());
			Assert.assertNotNull(actualInfo.getInitialDate());
			Assert.assertNotNull(actualInfo.getFinalDate());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}

	@Test
	public void deleteInfo_MissingInfoId_ReturnErrorCode() {
		try {
			// Setup

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url, HttpMethod.DELETE, HttpEntity.EMPTY, this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
	@Test
	public void deleteInfo_BadInfoId_ReturnErrorCode() {
		try {
			// Setup

			// Execute
			ResponseEntity<Info<T>> response = this.restTemplate
					.exchange(this.url + "/123", HttpMethod.DELETE, HttpEntity.EMPTY, this.infoTypeReference);

			// Assert
			Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

			// Verify
		} catch (Exception e) {
			Assert.fail("Unexpected exception was thrown");
		}
	}
	
}
