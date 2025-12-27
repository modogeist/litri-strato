package com.litri.strato.csm.configuration;

import com.litri.strato.csm.service.async.ChannelParticipantLinkService;
import com.litri.strato.csm.service.async.ChannelService;
import com.litri.strato.csm.service.async.ParticipantService;
import com.litri.strato.csm.service.sync.ProviderService;
import com.litri.strato.csm.service.sync.ServiceProviderLinkService;
import com.litri.strato.csm.service.sync.ServiceService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestServiceConfig {
	
	@Bean
	public ChannelParticipantLinkService channelParticipantLinkService() {
		return Mockito.spy(new ChannelParticipantLinkService());
	}

	@Bean
	public ChannelService channelService() {
		return Mockito.spy(new ChannelService());
	}

	@Bean
	public ParticipantService participantService() {
		return Mockito.spy(new ParticipantService());
	}

	@Bean
	public ProviderService providerService() {
		return Mockito.spy(new ProviderService());
	}

	@Bean
	public ServiceProviderLinkService serviceProviderLinkService() {
		return Mockito.spy(new ServiceProviderLinkService());
	}

	@Bean
	public ServiceService serviceService() {
		return Mockito.spy(new ServiceService());
	}

}
