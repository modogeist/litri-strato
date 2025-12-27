package com.litri.strato.csm.registry.local;

import com.litri.strato.dsm.SchemaId;
import com.litri.strato.csm.registry.RegistryClient;
import java.util.List;
import lombok.Builder;
import lombok.NonNull;

@Builder
public class LocalRegistryClient<T> implements RegistryClient<T> {
	
	@NonNull
	private LocalRegistryService<T> registryService;
	
	@Override
	public List<SchemaId> getSchemaIds() throws Exception {
		return this.registryService.getSchemaIds();
	}

	@Override
	public List<T> getSchemas() throws Exception {
		return this.registryService.getSchemas();
	}

	@Override
	public T getSchema(SchemaId schemaId) throws Exception {
		return this.registryService.getSchema(schemaId);
	}

	@Override
	public void putSchema(SchemaId schemaId, T schema) throws Exception {
		this.registryService.putSchema(schemaId, schema);
	}

	@Override
	public void removeSchema(SchemaId schemaId) throws Exception {
		this.registryService.removeSchema(schemaId);
	}
	
	@Override
	public void removeSchemas() throws Exception {
		this.registryService.removeSchemas();
	}
	
}
