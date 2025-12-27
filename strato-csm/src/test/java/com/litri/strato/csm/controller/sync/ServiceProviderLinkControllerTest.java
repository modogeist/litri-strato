package com.litri.strato.csm.controller.sync;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.InfoControllerTest;
import com.litri.strato.csm.service.sync.ServiceProviderLinkService;
import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.sync.ServiceProviderLink;
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
public class ServiceProviderLinkControllerTest extends InfoControllerTest<ServiceProviderLink> {
	
	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ServiceProviderLinkService serviceProviderLinkService;
	
	@Before
	public void setUp() {
		this.url = "/serviceproviderlinks";
		super.restTemplate = this.restTemplate;
		this.detailType = ServiceProviderLink.class;
		this.infoTypeReference = new ParameterizedTypeReference<Info<ServiceProviderLink>>() {};
		this.listInfoTypeReference = new ParameterizedTypeReference<List<Info<ServiceProviderLink>>>() {};
		this.detailSupplier = this::getSimpleDetail;
		this.infoService = this.serviceProviderLinkService;
		
		super.setUp();
	}
	
	private ServiceProviderLink getSimpleDetail() {
		return ServiceProviderLink.builder()
				.serviceInfoId(UUID.randomUUID())
				.providerInfoId(UUID.randomUUID())
				.build();
	}
	
}
