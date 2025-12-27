package com.litri.strato.csm.helper;

import com.litri.strato.dsm.SchemaId;

public class TestDataHelper {
	
	public static SchemaId getMinSchemaId(String prefix, String version) {
		return SchemaId.builder()
				.groupId(String.format("%s-groupId-test", prefix))
				.artifactId(String.format("%s-artifactId-test", prefix))
				.version(version)
				.build();
	}
	
}
