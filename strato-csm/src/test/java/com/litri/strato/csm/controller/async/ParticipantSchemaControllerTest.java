package com.litri.strato.csm.controller.async;

import com.litri.strato.csm.configuration.TestRegistryConfig;
import com.litri.strato.csm.controller.SchemaControllerTest;
import com.litri.strato.csm.helper.async.TestAsyncDataHelper;
import com.litri.strato.csm.service.SchemaService;
import com.litri.strato.dsm.async.ParticipantSchema;
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
public class ParticipantSchemaControllerTest extends SchemaControllerTest<ParticipantSchema> {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	SchemaService<ParticipantSchema> participantSchemaService;
	
	@Before
	public void setUp() {
		this.url = "/participantschemas";
		super.restTemplate = this.restTemplate;
		this.schemaType = ParticipantSchema.class;
		this.listSchemaTypeReference = new ParameterizedTypeReference<List<ParticipantSchema>>() {};
		this.schemaSupplier = TestAsyncDataHelper::getMinParticipantSchema;
		this.schemaService = this.participantSchemaService;

		super.setUp();
	}
	
}
