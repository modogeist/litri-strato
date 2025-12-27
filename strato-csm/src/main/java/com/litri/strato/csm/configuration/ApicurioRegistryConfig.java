package com.litri.strato.csm.configuration;

import com.litri.strato.csm.registry.RegistryClient;
import com.litri.strato.csm.registry.apicurio.ApicurioRegistryClient;
import com.litri.strato.csm.registry.apicurio.ApicurioRegistryProperties;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.SchemaType;
import com.litri.strato.dsm.async.BrokerSchema;
import com.litri.strato.dsm.async.ChannelSchema;
import com.litri.strato.dsm.async.MessageSchema;
import com.litri.strato.dsm.async.ParticipantSchema;
import com.litri.strato.dsm.sync.DataSchema;
import com.litri.strato.dsm.sync.ProviderSchema;
import com.litri.strato.dsm.sync.ServiceSchema;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("apicurio-registry")
@Configuration
public class ApicurioRegistryConfig {
	
	private static final int CONNECT_TIMEOUT = 10000;
	private static final int READ_TIMEOUT = 10000;
	
	@Autowired
	ApicurioRegistryProperties apicurioRegistryProperties;
	
	@Bean
	public SchemaService<DataSchema> dataSchemaService() {
		return this.getSchemaService(DataSchema.class, SchemaType.DATA);
	}
	
	@Bean
	public SchemaService<ServiceSchema> serviceSchemaService() {
		return this.getSchemaService(ServiceSchema.class, SchemaType.SERVICE);
	}
	
	@Bean
	public SchemaService<ProviderSchema> providerSchemaService() {
		return this.getSchemaService(ProviderSchema.class, SchemaType.PROVIDER);
	}
	
	@Bean
	public SchemaService<MessageSchema> messageSchemaService() {
		return this.getSchemaService(MessageSchema.class, SchemaType.MESSAGE);
	}
	
	@Bean
	public SchemaService<ChannelSchema> channelSchemaService() {
		return this.getSchemaService(ChannelSchema.class, SchemaType.CHANNEL);
	}
	
	@Bean
	public SchemaService<ParticipantSchema> participantSchemaService() {
		return this.getSchemaService(ParticipantSchema.class, SchemaType.CHANNEL);
	}
	
	@Bean
	public SchemaService<BrokerSchema> brokerSchemaService() {
		return this.getSchemaService(BrokerSchema.class, SchemaType.BROKER);
	}
	
	private SchemaService getSchemaService(Class responseType, SchemaType schemaType) {
		return SchemaService.builder()
				.registryClient(this.getRegistryClient(responseType, schemaType))
				.build();
	}
	
	private RegistryClient getRegistryClient(Class responseType, SchemaType schemaType) {
		return ApicurioRegistryClient.builder()
				.url(this.apicurioRegistryProperties.getUrl())
				.connectTimeout(CONNECT_TIMEOUT)
				.readTimeout(READ_TIMEOUT)
				.headers(Map.of(
						"WM_CONSUMER.ID", this.apicurioRegistryProperties.getWmConsumerId(),
						"WM_SVC.NAME", this.apicurioRegistryProperties.getWmServiceName(),
						"WM_SVC.ENV", this.apicurioRegistryProperties.getWmServiceEnv()))
				.responseType(responseType)
				.labels(this.apicurioRegistryProperties.getLabels())
				.properties(Stream
						.concat(Map.of("Schema-Type", schemaType.getValue()).entrySet().stream(),
								this.apicurioRegistryProperties.getProperties().entrySet().stream())
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
				.build();
	}
	
}
