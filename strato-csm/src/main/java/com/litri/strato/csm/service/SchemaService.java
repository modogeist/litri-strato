package com.litri.strato.csm.service;

import com.litri.strato.dsm.SchemaId;
import com.litri.strato.csm.registry.RegistryClient;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class SchemaService<T> {
	
	@NonNull
	RegistryClient<T> registryClient;
	
	public Collection<T> getAll() throws Exception {
		return this.registryClient.getSchemas();
	}
	
	public Collection<T> get(String groupId, String artifactId, String version) throws Exception {
		if (Objects.isNull(groupId) && Objects.isNull(artifactId) && Objects.isNull(version)) {
			return this.registryClient.getSchemas();
		}
		if (Objects.isNull(groupId) || Objects.isNull(artifactId) || Objects.isNull(version)) {
			throw new IllegalArgumentException("Either specify no criteria or all criteria (i.e. groupId, artifactId, version)");
		}
		SchemaId schemaId = SchemaId.builder()
				.groupId(groupId)
				.artifactId(artifactId)
				.version(version)
				.build();
		return List.of(this.get(schemaId));
	}
	
	public T get(@NonNull SchemaId schemaId) throws Exception {
		return this.registryClient.getSchema(schemaId);
	}
	
	public void put(@NonNull SchemaId schemaId, @NonNull T schema) throws Exception {
		this.registryClient.putSchema(schemaId, schema);
	}
	
	public void remove(@NonNull SchemaId schemaId) throws Exception {
		this.registryClient.removeSchema(schemaId);
	}
	
	public void removeAll() throws Exception {
		this.registryClient.removeSchemas();
	}
	
}
