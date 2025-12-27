package com.litri.strato.csm.controller.async;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.SchemaControllerTest;
import com.litri.strato.csm.helper.async.TestAsyncDataHelper;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.async.ChannelSchema;
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
public class ChannelSchemaControllerTest extends SchemaControllerTest<ChannelSchema> {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	SchemaService<ChannelSchema> channelSchemaService;
	
	@Before
	public void setUp() {
		this.url = "/channelschemas";
		super.restTemplate = this.restTemplate;
		this.schemaType = ChannelSchema.class;
		this.listSchemaTypeReference = new ParameterizedTypeReference<List<ChannelSchema>>() {};
		this.schemaSupplier = TestAsyncDataHelper::getMinChannelSchema;
		this.schemaService = this.channelSchemaService;

		super.setUp();
	}
	
}
