package com.litri.strato.dsm;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.dsm.BaseSchema.Reference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RefOption<T> {
	
	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@NonNull
	private Map map;

	public static RefOption from(Object object) throws Exception {
		if (Objects.isNull(object)) {
			return new RefOption();
		}
		String json = OBJECT_MAPPER.writeValueAsString(object);
		Map map = OBJECT_MAPPER.readValue(json, Map.class);
		return new RefOption(map);
	}

	@JsonAnyGetter
	public Map getMap() {
		return this.map;
	}

	@JsonAnySetter
	public void put(String key, Object value) {
		if (Objects.isNull(this.map)) {
			this.map = new HashMap();
		}
		this.map.put(key, value);
	}

	@JsonIgnore
	public boolean isReference() {
		return Objects.nonNull(this.map) && this.map.size() <= 3 && this.map.containsKey("$ref");
	}

	@JsonIgnore
	public Reference getReference() throws Exception {
		if (Objects.isNull(this.map)) {
			return null;
		}
		String json = OBJECT_MAPPER.writeValueAsString(this.map);
		Reference reference = OBJECT_MAPPER.readValue(json, Reference.class);
		return reference;
	}

	@JsonIgnore
	public T getObject(Class<T> type) throws Exception {
		if (Objects.isNull(this.map)) {
			return null;
		}
		String json = OBJECT_MAPPER.writeValueAsString(this.map);
		Object object = OBJECT_MAPPER.readValue(json, type);
		return (T) object;
	}

}
