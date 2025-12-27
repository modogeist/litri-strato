package com.litri.strato.csm.controller.async;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.InfoControllerTest;
import com.litri.strato.csm.service.async.ChannelParticipantLinkService;
import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.async.ChannelParticipantLink;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {TestRegistryConfig.class})
public class ChannelParticipantLinkControllerTest extends InfoControllerTest<ChannelParticipantLink> {
	
	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ChannelParticipantLinkService serviceProviderLinkService;
	
	@Before
	public void setUp() {
		this.url = "/channelparticipantlinks";
		super.restTemplate = this.restTemplate;
		this.detailType = ChannelParticipantLink.class;
		this.infoTypeReference = new ParameterizedTypeReference<Info<ChannelParticipantLink>>() {};
		this.listInfoTypeReference = new ParameterizedTypeReference<List<Info<ChannelParticipantLink>>>() {};
		this.detailSupplier = this::getSimpleDetail;
		this.infoService = this.serviceProviderLinkService;
		
		super.setUp();
	}
	
	private ChannelParticipantLink getSimpleDetail() {
		return ChannelParticipantLink.builder()
				.channelInfoId(UUID.randomUUID())
				.participantInfoId(UUID.randomUUID())
				.build();
	}
	
}
