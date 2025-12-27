package com.litri.strato.csm.controller.async;

import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.async.ChannelSchema;
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
public class ChannelSchemaController {
	
	@Autowired
	SchemaService<ChannelSchema> channelSchemaService;
	
	@GetMapping(value = "/channelschemas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<ChannelSchema> getChannelSchemas(
			@RequestParam(required = false) String groupId,
			@RequestParam(required = false) String artifactId,
			@RequestParam(required = false) String version) throws Exception {
		return this.channelSchemaService.get(groupId, artifactId, version).stream()
				.collect(Collectors.toList());
	}
	
	@PostMapping(value = "/channelschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void addChannelSchema(@RequestBody SchemaEntry<ChannelSchema> schemaEntry) throws Exception {
		this.channelSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(ChannelSchema.class));
	}
	
	@PutMapping(value = "/channelschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateChannelSchema(@RequestBody SchemaEntry<ChannelSchema> schemaEntry) throws Exception {
		this.channelSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(ChannelSchema.class));
	}
	
	@DeleteMapping(value = "/channelschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteChannelSchema(@RequestBody SchemaId schemaId) throws Exception {
		this.channelSchemaService.remove(schemaId);
	}
	
}
