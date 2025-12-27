package com.litri.strato.csm.controller.sync;

import com.litri.strato.dsm.sync.ProviderSchema;
import com.litri.strato.dsm.SchemaId;
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
public class ProviderSchemaController {
	
	@Autowired
	SchemaService<ProviderSchema> providerSchemaService;
	
	@GetMapping(value = "/providerschemas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<ProviderSchema> getProviderSchemas(
			@RequestParam(required = false) String groupId,
			@RequestParam(required = false) String artifactId,
			@RequestParam(required = false) String version) throws Exception {
		return this.providerSchemaService.get(groupId, artifactId, version).stream()
				.collect(Collectors.toList());
	}	
	
	@PostMapping(value = "/providerschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void addProviderSchema(@RequestBody SchemaEntry<ProviderSchema> schemaEntry) throws Exception {
		this.providerSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(ProviderSchema.class));
	}
	
	@PutMapping(value = "/providerschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateProviderSchema(@RequestBody SchemaEntry<ProviderSchema> schemaEntry) throws Exception {
		this.providerSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(ProviderSchema.class));
	}
	
	@DeleteMapping(value = "/providerschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteProviderSchema(@RequestBody SchemaId schemaId) throws Exception {
		this.providerSchemaService.remove(schemaId);
	}
	
}
