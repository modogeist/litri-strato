package com.litri.strato.csm.controller.sync;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.InfoControllerTest;
import com.litri.strato.csm.helper.TestDataHelper;
import com.litri.strato.csm.service.sync.ServiceService;
import com.litri.strato.dsm.Info;
import com.litri.strato.dsm.sync.ServiceDetail;
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
public class ServiceControllerTest extends InfoControllerTest<ServiceDetail> {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ServiceService serviceService;
	
	@Before
	public void setUp() {
		this.url = "/services";
		super.restTemplate = this.restTemplate;
		this.detailType = ServiceDetail.class;
		this.infoTypeReference = new ParameterizedTypeReference<Info<ServiceDetail>>() {};
		this.listInfoTypeReference = new ParameterizedTypeReference<List<Info<ServiceDetail>>>() {};
		this.detailSupplier = this::getSimpleDetail;
		this.infoService = this.serviceService;
		
		super.setUp();
	}
	
	private ServiceDetail getSimpleDetail() {
		return ServiceDetail.builder()
				.dataSchemaIds(Set.of(
						TestDataHelper.getMinSchemaId("data", "1"),
						TestDataHelper.getMinSchemaId("data", "2")))
				.serviceSchemaId(TestDataHelper.getMinSchemaId("service", "1"))
				.instanceId(UUID.randomUUID())
				.build();
	}

}
