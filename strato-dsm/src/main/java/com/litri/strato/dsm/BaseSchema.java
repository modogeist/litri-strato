package com.litri.strato.dsm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class BaseSchema {
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Contact {
		private String name;
		private String url;
		private String email;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Discriminator {
		@NonNull
		private String propertyName;
		private Map<String, String> mapping;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ExternalDocument {
		private String description;
		@NonNull
		private String url;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Info {
		@NonNull
		private String title;
		private String summary;
		private String description;
		private String termsOfService;
		private Contact contact;
		private License licence;
		@NonNull
		private String version;
	
		private Set<Tag> tags;
		private ExternalDocument externalDocs;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class License {
		@NonNull
		private String name;
		private String identifier;
		private String url;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class OAuthFlow {
		@NonNull
		private String authorizationUrl;
		@NonNull
		private String tokenUrl;
		private String refreshUrl;
		@NonNull
		private Map<String, String> scopes;
		@NonNull
		private Map<String, String> availableScopes;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class OAuthFlows {
		private OAuthFlow implicit;
		private OAuthFlow password;
		private OAuthFlow clientCredentials;
		private OAuthFlow authorizationCode;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Reference {
		@NonNull
		@JsonProperty("$ref")
		private String ref;
		private String summary;
		private String description;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Schema {
		@JsonProperty("$id")
		private String id;
		@JsonProperty("$schema")
		private String schema;
		private String title;
		private String description;
		private Type type;

		private String pattern;
		private Integer minimum;
		private Integer maximum;
		private Integer minLength;
		private Integer maxLength;
		private Integer minItems;
		private Integer maxItems;
		private Number multipleOf;
		private Boolean uniqueItems;

		@JsonProperty("default")
		private Object defaultValue;
		@JsonProperty("const")
		private Object constValue;
		@JsonProperty("enum")	
		private List<Object> enumValues;
		private List<Object> examples;
		private List<String> required;

		private Map<String, RefOption<Schema>> properties;
		private Map<String, RefOption<Schema>> additionalProperties;
		private Map<String, RefOption<Schema>> items;
		private Map<String, RefOption<Schema>> allOf;
		private Map<String, RefOption<Schema>> anyOf;
		private Map<String, RefOption<Schema>> oneOf;
		private RefOption<Schema> not;

		@JsonProperty("$defs")
		private Map<String, Schema> defs;

		private Map<String, List<String>> dependentRequired;
		private Map<String, List<Schema>> dependentSchemas;

		@JsonProperty("if")
		private RefOption<Schema> ifValue;
		@JsonProperty("then")
		private RefOption<Schema> thenValue;
		@JsonProperty("else")
		private RefOption<Schema> elseValue;
			
		private Discriminator discriminator;
		private Xml xml;
		private ExternalDocument externalDocs;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class SecurityScheme {
		@NonNull
		private String type;
		private String description;
		@NonNull
		private String name;
		@NonNull
		private String in;
		@NonNull
		private String scheme;
		private String bearerFormat;
		@NonNull
		private OAuthFlows flows;
		@NonNull
		private String openIdConnectUrl;
		private List<String> scopes;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Tag {
		@NonNull
		private String name;
		private String description;
		private ExternalDocument externalDocs;
	}

	@AllArgsConstructor
	public enum Type {
		ARRAY("array"),
		BOOLEAN("boolean"),
		NULL("null"),
		NUMBER("number"),
		OBJECT("object"),
		STRING("string");

		@Getter
		@JsonValue
		private final String value;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Xml {
		private String name;
		private String namespace;
		private String prefix;
		private Boolean attribute;
		private Boolean wrapped;
	}

}
