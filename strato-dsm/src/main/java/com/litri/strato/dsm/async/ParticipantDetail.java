package com.litri.strato.dsm.async;

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
public class ParticipantDetail {
	
	@NonNull
	private Set<SchemaId> messageSchemaIds;
	@NonNull
	private SchemaId channelSchemaId;
	@NonNull
	private SchemaId participantSchemaId;
	
	@NonNull
	private UUID instanceId;
	
}
