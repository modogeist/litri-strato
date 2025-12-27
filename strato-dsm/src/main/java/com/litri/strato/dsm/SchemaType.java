package com.litri.strato.dsm;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SchemaType {
	
	DATA("data"),
	SERVICE("service"),
	PROVIDER("provider"),
	
	MESSAGE("message"),
	CHANNEL("channel"),
	BROKER("broker");

	@Getter
	@JsonValue
	private final String value;
	
}
