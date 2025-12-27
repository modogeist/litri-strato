package com.litri.strato.csm.registry.local;

import com.litri.strato.dsm.SchemaId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public class LocalRegistryService<T> {
	
	@Builder.Default
	private Map<String, Map<String, Map<String, T>>> groupArtifactVersionSchemas = new ConcurrentHashMap();
	
	public List<SchemaId> getSchemaIds() {
		List<SchemaId> schemaIds = new ArrayList();
		this.groupArtifactVersionSchemas
				.forEach((groupId, groupValue) -> groupValue
						.forEach((artifactId, artifactValue) -> artifactValue
								.forEach((version, schema) -> schemaIds.add(SchemaId.builder()
										.groupId(groupId)
										.artifactId(artifactId)
										.version(version)
										.build()))));
		return schemaIds;
	}

	public List<T> getSchemas() {
		return this.groupArtifactVersionSchemas.values().stream()
				.map(Map::values)
				.flatMap(Collection::stream)
				.map(Map::values)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	public T getSchema(SchemaId schemaId) {
		return this.groupArtifactVersionSchemas
				.getOrDefault(schemaId.getGroupId(), Collections.emptyMap())
				.getOrDefault(schemaId.getArtifactId(), Collections.emptyMap())
				.get(schemaId.getVersion());
	}
	
	public void putSchema(SchemaId schemaId, T schema) {
		((Map) this.groupArtifactVersionSchemas
				.computeIfAbsent(schemaId.getGroupId(), key -> new ConcurrentHashMap())
				.computeIfAbsent(schemaId.getArtifactId(), key -> new ConcurrentHashMap()))
				.put(schemaId.getVersion(), schema);
	}
	
	public void removeSchema(SchemaId schemaId) {
		this.groupArtifactVersionSchemas
				.getOrDefault(schemaId.getGroupId(), Collections.emptyMap())
				.getOrDefault(schemaId.getArtifactId(), Collections.emptyMap())
				.remove(schemaId.getVersion());		
	}
	
	public void removeSchemas() {
		this.groupArtifactVersionSchemas.clear();
	}
	
}
