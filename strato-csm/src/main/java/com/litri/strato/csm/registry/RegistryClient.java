package com.litri.strato.csm.registry;

import com.litri.strato.dsm.SchemaId;
import java.util.List;

public interface RegistryClient<T> {
	
	public List<SchemaId> getSchemaIds() throws Exception;
	public List<T> getSchemas() throws Exception;
	public T getSchema(SchemaId schemaId) throws Exception;
	public void putSchema(SchemaId schemaId, T schema) throws Exception;
	public void removeSchema(SchemaId schemaId) throws Exception;
	public void removeSchemas() throws Exception;
	
}
