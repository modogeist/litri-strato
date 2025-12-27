package com.litri.strato.csm.controller.sync;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.InfoControllerTest;
import com.litri.strato.csm.helper.TestDataHelper;
import com.litri.strato.csm.service.sync.ProviderService;
import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.sync.ProviderDetail;
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
public class ProviderControllerTest extends InfoControllerTest<ProviderDetail> {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ProviderService providerService;
	
	@Before
	public void setUp() {
		this.url = "/providers";
		super.restTemplate = this.restTemplate;
		this.detailType = ProviderDetail.class;
		this.infoTypeReference = new ParameterizedTypeReference<Info<ProviderDetail>>() {};
		this.listInfoTypeReference = new ParameterizedTypeReference<List<Info<ProviderDetail>>>() {};
		this.detailSupplier = this::getSimpleDetail;
		this.infoService = this.providerService;
		
		super.setUp();
	}
	
	private ProviderDetail getSimpleDetail() {
		return ProviderDetail.builder()
				.serviceSchemaId(TestDataHelper.getMinSchemaId("service", "1"))
				.providerSchemaId(TestDataHelper.getMinSchemaId("provider", "1"))
				.instanceId(UUID.randomUUID())
				.serverIndex(0)
				.build();
	}

}
