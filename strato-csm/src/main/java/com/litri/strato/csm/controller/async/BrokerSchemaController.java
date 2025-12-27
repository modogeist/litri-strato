package com.litri.strato.csm.controller.async;

import com.litri.strato.dsm.SchemaId;
import com.litri.strato.dsm.async.BrokerSchema;
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
public class BrokerSchemaController {
	
	@Autowired
	SchemaService<BrokerSchema> brokerSchemaService;
	
	@GetMapping(value = "/brokerschemas", produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<BrokerSchema> getBrokerSchemas(
			@RequestParam(required = false) String groupId,
			@RequestParam(required = false) String artifactId,
			@RequestParam(required = false) String version) throws Exception {
		return this.brokerSchemaService.get(groupId, artifactId, version).stream()
				.collect(Collectors.toList());
	}
	
	@PostMapping(value = "/brokerschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void addBrokerSchema(@RequestBody SchemaEntry<BrokerSchema> schemaEntry) throws Exception {
		this.brokerSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(BrokerSchema.class));
	}
	
	@PutMapping(value = "/brokerschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void updateBrokerSchema(@RequestBody SchemaEntry<BrokerSchema> schemaEntry) throws Exception {
		this.brokerSchemaService.put(schemaEntry.getSchemaId(), schemaEntry.getSchema(BrokerSchema.class));
	}
	
	@DeleteMapping(value = "/brokerschemas", consumes = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteBrokerSchema(@RequestBody SchemaId schemaId) throws Exception {
		this.brokerSchemaService.remove(schemaId);
	}
	
}
