package com.litri.strato.csm.controller.async;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.InfoControllerTest;
import com.litri.strato.csm.helper.TestDataHelper;
import com.litri.strato.csm.service.async.ChannelService;
import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.async.ChannelDetail;
import java.util.List;
import java.util.Set;
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
public class ChannelControllerTest extends InfoControllerTest<ChannelDetail> {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ChannelService channelService;
	
	@Before
	public void setUp() {
		this.url = "/channels";
		super.restTemplate = this.restTemplate;
		this.detailType = ChannelDetail.class;
		this.infoTypeReference = new ParameterizedTypeReference<Info<ChannelDetail>>() {};
		this.listInfoTypeReference = new ParameterizedTypeReference<List<Info<ChannelDetail>>>() {};
		this.detailSupplier = this::getSimpleDetail;
		this.infoService = this.channelService;
		
		super.setUp();
	}
	
	private ChannelDetail getSimpleDetail() {
		return ChannelDetail.builder()
				.messageSchemaIds(Set.of(
						TestDataHelper.getMinSchemaId("message", "1"),
						TestDataHelper.getMinSchemaId("message", "2")))
				.channelSchemaId(TestDataHelper.getMinSchemaId("channel", "1"))
				.brokerSchemaId(TestDataHelper.getMinSchemaId("broker", "1"))
				.instanceId(UUID.randomUUID())
				.build();
	}

}
