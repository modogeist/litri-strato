package com.litri.strato.csm.configuration;

import com.litri.strato.csm.registry.local.LocalRegistryClient;
import com.litri.strato.csm.registry.local.LocalRegistryService;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.async.BrokerSchema;
import com.litri.strato.dsm.async.ChannelSchema;
import com.litri.strato.dsm.async.MessageSchema;
import com.litri.strato.dsm.async.ParticipantSchema;
import com.litri.strato.dsm.sync.DataSchema;
import com.litri.strato.dsm.sync.ProviderSchema;
import com.litri.strato.dsm.sync.ServiceSchema;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestRegistryConfig {
	
	@Bean
	public SchemaService<DataSchema> dataSchemaService() {
		return this.getSchemaService();
	}
	
	@Bean
	public SchemaService<ServiceSchema> serviceSchemaService() {
		return this.getSchemaService();
	}
	
	@Bean
	public SchemaService<ProviderSchema> providerSchemaService() {
		return this.getSchemaService();
	}
	
	@Bean
	public SchemaService<MessageSchema> messageSchemaService() {
		return this.getSchemaService();
	}
	
	@Bean
	public SchemaService<ChannelSchema> channelSchemaService() {
		return this.getSchemaService();
	}
	
	@Bean
	public SchemaService<ParticipantSchema> participantSchemaService() {
		return this.getSchemaService();
	}
	
	@Bean
	public SchemaService<BrokerSchema> brokerSchemaService() {
		return this.getSchemaService();
	}
	
	private SchemaService getSchemaService() {
		return Mockito.spy(SchemaService.builder()
				.registryClient(LocalRegistryClient.builder()
						.registryService(LocalRegistryService.builder().build())
						.build())
				.build());
	}
	
}
