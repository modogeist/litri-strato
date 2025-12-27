package com.litri.strato.csm.controller.sync;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.SchemaControllerTest;
import com.litri.strato.csm.helper.sync.TestSyncDataHelper;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.sync.DataSchema;
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
public class DataSchemaControllerTest extends SchemaControllerTest<DataSchema> {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	SchemaService<DataSchema> dataSchemaService;
	
	@Before
	public void setUp() {
		this.url = "/dataschemas";
		super.restTemplate = this.restTemplate;
		this.schemaType = DataSchema.class;
		this.listSchemaTypeReference = new ParameterizedTypeReference<List<DataSchema>>() {};
		this.schemaSupplier = TestSyncDataHelper::getMinDataSchema;
		this.schemaService = this.dataSchemaService;

		super.setUp();
	}
	
}
