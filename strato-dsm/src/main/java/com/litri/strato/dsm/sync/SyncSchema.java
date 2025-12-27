package com.litri.strato.dsm.sync;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.litri.strato.dsm.BaseSchema.ExternalDocument;
import com.litri.strato.dsm.BaseSchema.Schema;
import com.litri.strato.dsm.BaseSchema.SecurityScheme;
import com.litri.strato.dsm.RefOption;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class SyncSchema {
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Components {
		private Map<String, RefOption<Schema>> schemas; // Nonstandard reference
		private Map<String, RefOption<Response>> responses;
		private Map<String, RefOption<Parameter>> parameters;
		private Map<String, RefOption<Example>> examples;
		private Map<String, RefOption<RequestBody>> requestBodies;
		private Map<String, RefOption<Parameter>> headers;
		private Map<String, RefOption<SecurityScheme>> securitySchemes;
		private Map<String, RefOption<Link>> links;
		private Map<String, RefOption<Path>> callbacks;
		private Map<String, RefOption<Path>> pathItems;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Encoding {
		private String contentType;
		private Map<String, RefOption<Path>> headers;
		private String style;
		private Boolean explode;
		private Boolean allowReserved;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Example {
		private String summary;
		private String description;
		private Object value; // Any
		private String externalValue;
	}
	
	@AllArgsConstructor
	public static enum HttpLocation {
		COOKIE("cookie"),
		HEADER("header"),
		QUERY("query"),
		PATH("path");

		@Getter
		@JsonValue
		private final String value;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Link {
		private String operationRef;
		private String operationId;
		private Map<String, Object> parameters; // Any
		private Object requestBody; // Any
		private String description;
		private Server server;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MediaType {
		private RefOption<Schema> schema; // Nonstandard reference
		private Object example; // Any
		private Map<String, RefOption<Example>> examples;
		private Map<String, Encoding> encoding;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Operation {
		private List<String> tags;
		private String summary;
		private String description;
		private ExternalDocument externalDocs;
		private String operationId;
		private List<RefOption<Parameter>> parameters;
		private RefOption<RequestBody> requestBody;
		private Map<String, RefOption<Response>> responses;
		private Map<String, RefOption<Path>> callbacks;
		private Boolean deprecated;
		private Map<String, List<String>> security;
		private Server server;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Parameter {
		@NonNull
		private String name;
		@NonNull
		private HttpLocation in;
		private String description;
		@NonNull
		private Boolean required;
		private Boolean deprecated;
		private Boolean allowEmptyValue;
		private String style;
		private Boolean explode;
		private Boolean allowReserved;
		private Schema schema;
		private Object example; // Any
		private Map<String, RefOption<Example>> examples;
		private Map<String, MediaType> content;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Path {
		@JsonProperty("$ref")
		private String ref;
		private String summary;
		private String description;
		private Operation get;
		private Operation put;
		private Operation post;
		private Operation delete;
		private Operation options;
		private Operation head;
		private Operation patch;
		private Operation trace;
		private List<Server> servers;
		private List<RefOption<Parameter>> parameters;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Response {
		@NonNull
		private String description;
		private Map<String, RefOption<Parameter>> headers;
		private Map<String, MediaType> content;
		private Map<String, RefOption<Link>> links;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class RequestBody {
		private String description;
		@NonNull
		private Map<String, MediaType> content;
		private Boolean required;

	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Server {
		@NonNull
		private String url;
		private String description;
		private Map<String, ServerVariable> variables;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ServerVariable {
		@JsonProperty("enum")
		private List<String> enumValues;
		@NonNull
		@JsonProperty("default")
		private String defaultValue;
		private String description;
	}
	
}
