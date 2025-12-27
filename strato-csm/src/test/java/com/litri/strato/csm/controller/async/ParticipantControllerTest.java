package com.litri.strato.csm.controller.async;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.InfoControllerTest;
import com.litri.strato.csm.helper.TestDataHelper;
import com.litri.strato.csm.service.async.ParticipantService;
import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.async.ParticipantDetail;
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
public class ParticipantControllerTest extends InfoControllerTest<ParticipantDetail> {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ParticipantService participantService;
	
	@Before
	public void setUp() {
		this.url = "/participants";
		super.restTemplate = this.restTemplate;
		this.detailType = ParticipantDetail.class;
		this.infoTypeReference = new ParameterizedTypeReference<Info<ParticipantDetail>>() {};
		this.listInfoTypeReference = new ParameterizedTypeReference<List<Info<ParticipantDetail>>>() {};
		this.detailSupplier = this::getSimpleDetail;
		this.infoService = this.participantService;
		
		super.setUp();
	}
		
	private ParticipantDetail getSimpleDetail() {
		return ParticipantDetail.builder()
				.messageSchemaIds(Set.of(
						TestDataHelper.getMinSchemaId("message", "1"),
						TestDataHelper.getMinSchemaId("message", "2")))
				.channelSchemaId(TestDataHelper.getMinSchemaId("channel", "1"))
				.participantSchemaId(TestDataHelper.getMinSchemaId("participant", "1"))
				.instanceId(UUID.randomUUID())
				.build();
	}

}
