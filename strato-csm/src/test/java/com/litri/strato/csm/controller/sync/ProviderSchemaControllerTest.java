package com.litri.strato.csm.controller.sync;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.SchemaControllerTest;
import com.litri.strato.csm.helper.sync.TestSyncDataHelper;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.sync.ProviderSchema;
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
public class ProviderSchemaControllerTest extends SchemaControllerTest<ProviderSchema> {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	SchemaService<ProviderSchema> providerSchemaService;
	
	@Before
	public void setUp() {
		this.url = "/providerschemas";
		super.restTemplate = this.restTemplate;
		this.schemaType = ProviderSchema.class;
		this.listSchemaTypeReference = new ParameterizedTypeReference<List<ProviderSchema>>() {};
		this.schemaSupplier = TestSyncDataHelper::getMinProviderSchema;
		this.schemaService = this.providerSchemaService;

		super.setUp();
	}
	
}
