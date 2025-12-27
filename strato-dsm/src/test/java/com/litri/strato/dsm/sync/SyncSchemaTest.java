package com.litri.strato.dsm.sync;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.dsm.BaseSchema.Reference;
import com.litri.strato.dsm.RefOption;
import com.litri.strato.dsm.sync.SyncSchema.Encoding;
import com.litri.strato.dsm.sync.SyncSchema.Path;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class SyncSchemaTest {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void encoding_SingleReferenceHeaders_DeserializeReferenceHeaders() throws Exception {
		// Setup
		Reference expectedReference = Reference.builder()
				.ref("ref-test")
				.summary("summary-test")
				.description("description-test")
				.build();
		RefOption<Path> expectedRefOption = RefOption.from(expectedReference);
		Encoding expectedEncoding = Encoding.builder()
				.contentType("contentType-test")
				.headers(Map.of("header-test", expectedRefOption))
				.build();
		
		// Execute
		String json = this.objectMapper.writeValueAsString(expectedEncoding);
		Encoding actualEncoding = this.objectMapper.readValue(json, Encoding.class);
		
		// Assert
		Assert.assertNotNull(actualEncoding);
		Assert.assertEquals(expectedEncoding, actualEncoding);
		Assert.assertTrue(actualEncoding.getHeaders().get("header-test").isReference());
		Assert.assertEquals(expectedReference, actualEncoding.getHeaders().get("header-test").getReference());
		
		// Verify
	}
	
	@Test
	public void encoding_SinglePathHeaders_DeserializePathHeaders() throws Exception {
		// Setup
		Path expectedPath = Path.builder()
				.ref("ref-test")
				.summary("summary-test")
				.description("description-test")
				.build();
		RefOption<Path> expectedRefOption = RefOption.from(expectedPath);
		Encoding expectedEncoding = Encoding.builder()
				.contentType("contentType-test")
				.headers(Map.of("header-test", expectedRefOption))
				.build();
		
		// Execute
		String json = this.objectMapper.writeValueAsString(expectedEncoding);
		Encoding actualEncoding = this.objectMapper.readValue(json, Encoding.class);
		
		// Assert
		Assert.assertNotNull(actualEncoding);
		Assert.assertEquals(expectedEncoding, actualEncoding);
		Assert.assertFalse(actualEncoding.getHeaders().get("header-test").isReference());
		Assert.assertEquals(expectedPath, actualEncoding.getHeaders().get("header-test").getObject(Path.class));
		
		// Verify
	}
	
}
