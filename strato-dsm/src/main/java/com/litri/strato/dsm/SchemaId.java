package com.litri.strato.dsm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemaId {
	
	@NonNull
	private String groupId;
	@NonNull
	private String artifactId;
	@NonNull
	private String version;

}
