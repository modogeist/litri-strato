package com.litri.strato.dsm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.dsm.SchemaId;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SchemaEntry<T> {
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	@NonNull
	private SchemaId schemaId;
	@NonNull
	private Map schema;
	
	public static SchemaEntry fromObject(SchemaId schemaId, Object object) throws Exception {
		String json = OBJECT_MAPPER.writeValueAsString(object);
		Map schema = OBJECT_MAPPER.readValue(json, Map.class);
		return new SchemaEntry(schemaId, schema);
	}

	public static SchemaEntry fromString(SchemaId schemaId, String json) throws Exception {
		Map schema = OBJECT_MAPPER.readValue(json, Map.class);
		return new SchemaEntry(schemaId, schema);
	}

	@JsonIgnore
	public T getSchema(Class<T> type) throws Exception {
		if (Objects.isNull(this.schema)) {
			return null;
		}
		String json = OBJECT_MAPPER.writeValueAsString(this.schema);
		Object object = OBJECT_MAPPER.readValue(json, type);
		return (T) object;
	}
	
}
