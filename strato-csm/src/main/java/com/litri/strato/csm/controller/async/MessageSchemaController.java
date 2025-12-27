package com.litri.strato.csm.controller.async;

import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.async.MessageSchema;
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
public class MessageSchemaController {
	
	@Autowired
	SchemaService<MessageSchema> messageSchemaService;
	
	@GetMapping(value = "/messageschemas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<MessageSchema> getMessageSchemas(
			@RequestParam(required = false) String groupId,
			@RequestParam(required = false) String artifactId,
			@RequestParam(required = false) String version) throws Exception {
		return this.messageSchemaService.get(groupId, artifactId, version).stream()
				.collect(Collectors.toList());
	}
	
	@PostMapping(value = "/messageschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void addMessageSchema(@RequestBody SchemaEntry<MessageSchema> schemaEntry) throws Exception {
		this.messageSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(MessageSchema.class));
	}
	
	@PutMapping(value = "/messageschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateMessageSchema(@RequestBody SchemaEntry<MessageSchema> schemaEntry) throws Exception {
		this.messageSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(MessageSchema.class));
	}
	
	@DeleteMapping(value = "/messageschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteMessageSchema(@RequestBody SchemaId schemaId) throws Exception {
		this.messageSchemaService.remove(schemaId);
	}
	
}
