package com.litri.strato.dsm.sync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.litri.strato.dsm.SchemaId;
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
public class ProviderDetail {
	
	@NonNull
	private SchemaId serviceSchemaId;
	@NonNull
	private SchemaId providerSchemaId;
	
	@NonNull
	private UUID instanceId;
	@NonNull
	private Integer serverIndex;
	
}
