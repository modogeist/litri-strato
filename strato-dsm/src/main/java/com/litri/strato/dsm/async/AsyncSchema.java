package com.litri.strato.dsm.async;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.litri.strato.dsm.BaseSchema.ExternalDocument;
import com.litri.strato.dsm.BaseSchema.Reference;
import com.litri.strato.dsm.BaseSchema.Schema;
import com.litri.strato.dsm.BaseSchema.SecurityScheme;
import com.litri.strato.dsm.BaseSchema.Tag;
import com.litri.strato.dsm.RefOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class AsyncSchema {
	
	@AllArgsConstructor
	public enum Action {
		SEND("send"),
		RECEIVE("receive");

		@Getter
		@JsonValue
		private final String value;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Channel {
		private String address;
		private Map<String, RefOption<Message>> messages;
		private String title;
		private String summary;
		private String description;
		private List<Reference> servers;
		private Map<String, RefOption<Parameter>> parameters;
		private List<Tag> tags;
		private ExternalDocument externalDocs;
		private Map<String, RefOption<ChannelBinding>> bindings;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ChannelBinding {
		// TODO: Reserved for future use.
		private Map map;

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
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Components {
		private Map<String, RefOption<Schema>> schemas;
		private Map<String, RefOption<Server>> servers;
		private Map<String, RefOption<Channel>> channels;
		private Map<String, RefOption<Operation>> operations;
		private Map<String, RefOption<Message>> messages;
		private Map<String, RefOption<SecurityScheme>> securitySchemes;
		private Map<String, RefOption<ServerVariable>> serverVariables;
		private Map<String, RefOption<Parameter>> parameters;
		private Map<String, RefOption<CorrelationId>> correlationIds;
		private Map<String, RefOption<OperationReply>> replies;
		private Map<String, RefOption<OperationReplyAddress>> replyAddresses;
		private Map<String, RefOption<ExternalDocument>> externalDocs;
		private Map<String, RefOption<Tag>> tags;
		private Map<String, RefOption<OperationTrait>> operationTraits;
		private Map<String, RefOption<MessageTrait>> messageTraits;
		private Map<String, RefOption<ServerBinding>> serverBindings;
		private Map<String, RefOption<ChannelBinding>> channelBindings;
		private Map<String, RefOption<OperationBinding>> operationBindings;
		private Map<String, RefOption<MessageBinding>> messageBindings;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CorrelationId {
		private String description;
		private String location;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Message {
		private RefOption<Schema> headers;
		private RefOption<Schema> payload;
		private RefOption<CorrelationId> correlationId;
		private String contentType;
		private String name;
		private String title;
		private String summary;
		private String description;
		private List<Tag> tags;
		private ExternalDocument externalDocs;
		private Map<String, RefOption<MessageBinding>> bindings;
		private List<MessageExample> examples;
		private List<RefOption<MessageTrait>> traits;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MessageBinding {
		// TODO: Reserved for future use.
		private Map map;

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
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MessageExample {
		private Map<String, Object> headers;
		private Map<String, Object> payload;
		private String name;
		private String summary;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class MessageTrait {
		private RefOption<Schema> headers;
		private RefOption<CorrelationId> correlationId;
		private String contentType;
		private String name;
		private String title;
		private String summary;
		private String description;
		private List<Tag> tags;
		private ExternalDocument externalDocs;
		private Map<String, RefOption<MessageBinding>> bindings;
		private List<MessageExample> examples;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Operation {
		private Action action;
		private Reference channel;
		private String title;
		private String summary;
		private String description;
		private List<RefOption<SecurityScheme>> security;
		private List<Tag> tags;
		private ExternalDocument externalDocs;
		private Map<String, RefOption<OperationBinding>> bindings;
		private List<RefOption<OperationTrait>> traits;
		private List<Reference> messages;
		private RefOption<OperationReply> reply;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class OperationBinding {
		// TODO: Reserved for future use.
		private Map map;

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
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class OperationReply {
		private RefOption<OperationReplyAddress> address;
		private Reference channel;
		private List<Reference> messages;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class OperationReplyAddress {
		private String description;
		private String location;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class OperationTrait {
		private String title;
		private String summary;
		private String description;
		private List<RefOption<SecurityScheme>> security;
		private List<Tag> tags;
		private ExternalDocument externalDocs;
		private Map<String, RefOption<OperationBinding>> bindings;
	}
	
	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Parameter {
		@JsonProperty("enum")
		private List<String> enumValues;
		@JsonProperty("default")
		private String defaultValue;
		private String description;
		private List<String> examples;
		private String location;
	}

	@AllArgsConstructor
	public enum Protocol {
		AMQP("amqp"),
		AMQPS("amqps"),
		HTTP("http"),
		HTTPS("https"),
		IBMMQ("ibmmq"),
		JMS("jms"),
		KAFKA("kafka"),
		KAFKA_SECURE("kafka-secure"),
		ANYPOINTMQ("anypointmq"),
		MQTT("mqtt"),
		SECURE_MQTT("secure-mqtt"),
		SOLACE("solace"),
		STOMP("stomp"),
		STOMPS("stomps"),
		WS("ws"),
		WSS("wss"),
		MERCURE("mercure"),
		GOOGLEPUBSUB("googlepubsub"),
		PULSAR("pulsar");

		@Getter
		@JsonValue
		private final String value;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Server {
		@NonNull
		private String host;
		@NonNull
		private Protocol protocol;
		private String protocolVersion;
		private String pathname;
		private String description;
		private String title;
		private String summary;
		private Map<String, RefOption<ServerVariable>> variables;
		private List<RefOption<SecurityScheme>> security;
		private List<Tag> tags;
		private ExternalDocument externalDocs;
		private Map<String, RefOption<ServerBinding>> bindings;
	}

	@Builder
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ServerBinding {
		// TODO: Reserved for future use.
		private Map map;

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
		private List<String> examples;
		
	}
	
}
