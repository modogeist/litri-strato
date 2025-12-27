package com.litri.strato.csm.controller.async;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.SchemaControllerTest;
import com.litri.strato.csm.helper.async.TestAsyncDataHelper;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.async.MessageSchema;
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
public class MessageSchemaControllerTest extends SchemaControllerTest<MessageSchema> {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	SchemaService<MessageSchema> messageSchemaService;
	
	@Before
	public void setUp() {
		this.url = "/messageschemas";
		super.restTemplate = this.restTemplate;
		this.schemaType = MessageSchema.class;
		this.listSchemaTypeReference = new ParameterizedTypeReference<List<MessageSchema>>() {};
		this.schemaSupplier = TestAsyncDataHelper::getMinMessageSchema;
		this.schemaService = this.messageSchemaService;

		super.setUp();
	}
		
}
