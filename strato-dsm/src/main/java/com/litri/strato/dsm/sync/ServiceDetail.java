package com.litri.strato.dsm.sync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.litri.strato.dsm.SchemaId;
import java.util.Set;
import java.util.UUID;
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
public class ServiceDetail {
	
	@NonNull
	private Set<SchemaId> dataSchemaIds;
	@NonNull
	private SchemaId serviceSchemaId;
	
	@NonNull
	private UUID instanceId;
	
}
