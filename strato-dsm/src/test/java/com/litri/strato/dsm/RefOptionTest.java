package com.litri.strato.dsm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.dsm.BaseSchema.Reference;
import com.litri.strato.dsm.sync.SyncSchema.Example;
import com.litri.strato.dsm.sync.SyncSchema.Response;
import org.junit.Assert;
import org.junit.Test;

public class RefOptionTest {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void getMap_RandomKeyValue_SerializeJson() throws Exception {
		// Setup
		RefOption refOption = new RefOption();
		refOption.put("key-test", "value-test");
		
		// Execute
		String json = this.objectMapper.writeValueAsString(refOption);
		
		// Assert
		Assert.assertTrue(json.contains("key-test"));
		Assert.assertTrue(json.contains("value-test"));
		
		// Verify
	}
	
	@Test
	public void put_JsonReference_DeserializeReference() throws Exception {
		// Setup
		Reference expectedReference = Reference.builder()
				.ref("ref-test")
				.summary("summary-test")
				.description("description-test")
				.build();
		String json = this.objectMapper.writeValueAsString(expectedReference);
		
		// Execute
		RefOption<Response> refOption = this.objectMapper.readValue(json, RefOption.class);
		
		// Assert
		Assert.assertNotNull(refOption);
		Assert.assertTrue(refOption.isReference());
		Assert.assertEquals(expectedReference, refOption.getReference());
		
		// Verify
	}
	
	@Test
	public void put_JsonExample_DeserializeExample() throws Exception {
		// Setup
		Example expectedExample = Example.builder()
				.summary("summary-test")
				.description("description-test")
				.value(0)
				.build();
		String json = this.objectMapper.writeValueAsString(expectedExample);
		
		// Execute
		RefOption<Example> refOption = this.objectMapper.readValue(json, RefOption.class);
		
		// Assert
		Assert.assertNotNull(refOption);
		Assert.assertFalse(refOption.isReference());
		Assert.assertEquals(expectedExample, refOption.getObject(Example.class));
		
		// Verify
	}	
	
	@Test
	public void from_ObjectReference_DeserializeReference() throws Exception {
		// Setup
		Reference expectedReference = Reference.builder()
				.ref("ref-test")
				.summary("summary-test")
				.description("description-test")
				.build();
		
		// Execute
		RefOption<Response> refOption = RefOption.from(expectedReference);
		
		// Assert
		Assert.assertTrue(refOption.isReference());
		Assert.assertEquals(expectedReference, refOption.getReference());
		
		// Verify
	}
	
	@Test
	public void from_ObjectExample_DeserializeExample() throws Exception {
		// Setup
		Example expectedExample = Example.builder()
				.summary("summary-test")
				.description("description-test")
				.value(0)
				.build();
		
		// Execute
		RefOption<Example> refOption = RefOption.from(expectedExample);
		
		// Assert
		Assert.assertFalse(refOption.isReference());
		Assert.assertEquals(expectedExample, refOption.getObject(Example.class));
		
		// Verify
	}	
	
}
