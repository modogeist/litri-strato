package com.litri.strato.csm.controller.sync;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.SchemaControllerTest;
import com.litri.strato.csm.helper.sync.TestSyncDataHelper;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.sync.ServiceSchema;
import java.util.List;
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
public class ServiceSchemaControllerTest extends SchemaControllerTest<ServiceSchema> {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	SchemaService<ServiceSchema> serviceSchemaService;
	
	@Before
	public void setUp() {
		this.url = "/serviceschemas";
		super.restTemplate = this.restTemplate;
		this.schemaType = ServiceSchema.class;
		this.listSchemaTypeReference = new ParameterizedTypeReference<List<ServiceSchema>>() {};
		this.schemaSupplier = TestSyncDataHelper::getMinServiceSchema;
		this.schemaService = this.serviceSchemaService;

		super.setUp();
	}
	
}
