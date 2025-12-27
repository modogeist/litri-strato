package com.litri.strato.csm.controller.sync;

import com.litri.strato.dsm.sync.DataSchema;
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
public class DataSchemaController {
	
	@Autowired
	SchemaService<DataSchema> dataSchemaService;
	
	@GetMapping(value = "/dataschemas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<DataSchema> getDataSchemas(
			@RequestParam(required = false) String groupId,
			@RequestParam(required = false) String artifactId,
			@RequestParam(required = false) String version) throws Exception {
		return this.dataSchemaService.get(groupId, artifactId, version).stream()
				.collect(Collectors.toList());
	}
	
	@PostMapping(value = "/dataschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void addDataSchema(@RequestBody SchemaEntry<DataSchema> schemaEntry) throws Exception {
		this.dataSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(DataSchema.class));
	}
	
	@PutMapping(value = "/dataschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateDataSchema(@RequestBody SchemaEntry<DataSchema> schemaEntry) throws Exception {
		this.dataSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(DataSchema.class));
	}
	
	@DeleteMapping(value = "/dataschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteDataSchema(@RequestBody SchemaId schemaId) throws Exception {
		this.dataSchemaService.remove(schemaId);
	}
	
}
