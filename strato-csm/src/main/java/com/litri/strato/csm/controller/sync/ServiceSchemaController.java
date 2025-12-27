package com.litri.strato.csm.controller.sync;

import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.sync.ServiceSchema;
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
public class ServiceSchemaController {
	
	@Autowired
	SchemaService<ServiceSchema> serviceSchemaService;
	
	@GetMapping(value = "/serviceschemas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<ServiceSchema> getServiceSchemas(
			@RequestParam(required = false) String groupId,
			@RequestParam(required = false) String artifactId,
			@RequestParam(required = false) String version) throws Exception {
		return this.serviceSchemaService.get(groupId, artifactId, version).stream()
				.collect(Collectors.toList());
	}
	
	@PostMapping(value = "/serviceschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void addServiceSchema(@RequestBody SchemaEntry<ServiceSchema> schemaEntry) throws Exception {
		this.serviceSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(ServiceSchema.class));
	}
	
	@PutMapping(value = "/serviceschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateServiceSchema(@RequestBody SchemaEntry<ServiceSchema> schemaEntry) throws Exception {
		this.serviceSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(ServiceSchema.class));
	}
	
	@DeleteMapping(value = "/serviceschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteServiceSchema(@RequestBody SchemaId schemaId) throws Exception {
		this.serviceSchemaService.remove(schemaId);
	}
	
}
