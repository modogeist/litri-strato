package com.litri.strato.csm.configuration;

import com.litri.strato.csm.service.async.ChannelParticipantLinkService;
import com.litri.strato.csm.service.async.ChannelService;
import com.litri.strato.csm.service.async.ParticipantService;
import com.litri.strato.csm.service.sync.ProviderService;
import com.litri.strato.csm.service.sync.ServiceProviderLinkService;
import com.litri.strato.csm.service.sync.ServiceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

	@Value("${channel-participate-link.timeout:0}")
	private long channelParticipantLinkTimeout;
	
	@Bean
	public ChannelParticipantLinkService channelParticipantLinkService() {
		return (this.channelParticipantLinkTimeout == 0)
				? new ChannelParticipantLinkService()
				: new ChannelParticipantLinkService(this.channelParticipantLinkTimeout);
	}

	@Value("${channel.timeout:0}")
	private long channelTimeout;
	
	@Bean
	public ChannelService channelService() {
		return (this.channelTimeout == 0)
				? new ChannelService()
				: new ChannelService(this.channelTimeout);
	}

	@Value("${participant.timeout:0}")
	private long participantTimeout;
	
	@Bean
	public ParticipantService participantService() {
		return (this.participantTimeout == 0)
				? new ParticipantService()
				: new ParticipantService(this.participantTimeout);
	}

	@Value("${provider.timeout:0}")
	private long providerTimeout;
	
	@Bean
	public ProviderService providerService() {
		return (this.providerTimeout == 0)
				? new ProviderService()
				: new ProviderService(this.providerTimeout);
	}

	@Value("${service-provider-link.timeout:0}")
	private long serviceProviderLinkTimeout;
	
	@Bean
	public ServiceProviderLinkService serviceProviderLinkService() {
		return (this.serviceProviderLinkTimeout == 0)
				? new ServiceProviderLinkService()
				: new ServiceProviderLinkService(this.serviceProviderLinkTimeout);
	}

	@Value("${service.timeout:0}")
	private long serviceTimeout;
	
	@Bean
	public ServiceService serviceService() {
		return (this.serviceTimeout == 0)
				? new ServiceService()
				: new ServiceService(this.serviceTimeout);
	}

}
