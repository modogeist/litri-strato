package com.litri.strato.csm.registry.apicurio;

import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.sync.DataSchema;
import java.util.List;
import java.util.Map;
import org.junit.Ignore;
import org.junit.Test;

public class ApicurioRegistryClientIT {
	
	@Ignore
	@Test
	public void getSchemaIds_WithLabelsProperties_Success() throws Exception {
		// Setup
		ApicurioRegistryClient<DataSchema> client = ApicurioRegistryClient.<DataSchema>builder()
				.url("https://int-metadata-engine.stage.walmart.com")
				.connectTimeout(10000)
				.readTimeout(10000)
				.headers(Map.of(
						"WM_CONSUMER.ID", "d0a57373-064c-471e-9e6a-3e22eef0110c",
						"WM_SVC.NAME", "SAMS-SCHEMA-REGISTRY-SERVICE",
						"WM_SVC.ENV", "stage"))
				.responseType(DataSchema.class)
				.labels(List.of("MemBrain"))
				.properties(Map.of())
				.build();
		
		// Execute
		List<SchemaId> response = client.getSchemaIds();
		
		// Assert
		System.out.println(response);
		
		// Verify
	}
	
	@Test
	public void getSchema_WithGroupIdArtifactIdVersion_Success() throws Exception {
		// Setup
		ApicurioRegistryClient<DataSchema> client = ApicurioRegistryClient.<DataSchema>builder()
				.url("https://int-metadata-engine.stage.walmart.com")
				.connectTimeout(10000)
				.readTimeout(10000)
				.headers(Map.of(
						"WM_CONSUMER.ID", "d0a57373-064c-471e-9e6a-3e22eef0110c",
						"WM_SVC.NAME", "SAMS-SCHEMA-REGISTRY-SERVICE",
						"WM_SVC.ENV", "stage"))
				.responseType(DataSchema.class)
				.build();
		
		SchemaId schemaId = SchemaId.builder()
				.groupId("MEMBRAIN")
				.artifactId("000001")
				.version("1")
				.build();
		
		// Execute
		DataSchema response = client.getSchema(schemaId);
		
		// Assert
		System.out.println(response);
		
		// Verify
	}
	
}
