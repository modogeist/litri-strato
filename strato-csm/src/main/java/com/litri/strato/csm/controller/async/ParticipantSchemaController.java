package com.litri.strato.csm.controller.async;

import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.async.ParticipantSchema;
import com.litri.strato.dsm.dto.SchemaEntry;
import com.litri.strato.csm.service.SchemaService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantSchemaController {
	
	@Autowired
	SchemaService<ParticipantSchema> participantSchemaService;
	
	@GetMapping(value = "/participantschemas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<ParticipantSchema> getParticipantSchemas(
			@RequestParam(required = false) String groupId,
			@RequestParam(required = false) String artifactId,
			@RequestParam(required = false) String version) throws Exception {
		return this.participantSchemaService.get(groupId, artifactId, version).stream()
				.collect(Collectors.toList());
	}
	
	@PostMapping(value = "/participantschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void addParticipantSchema(@RequestBody SchemaEntry<ParticipantSchema> schemaEntry) throws Exception {
		this.participantSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(ParticipantSchema.class));
	}
	
	@PutMapping(value = "/participantschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateParticipantSchema(@RequestBody SchemaEntry<ParticipantSchema> schemaEntry) throws Exception {
		this.participantSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(ParticipantSchema.class));
	}
	
	@DeleteMapping(value = "/participantschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteParticipantSchema(@RequestBody SchemaId schemaId) throws Exception {
		this.participantSchemaService.remove(schemaId);
	}
	
}
