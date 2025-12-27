package com.litri.strato.vdm.compute;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionMap;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

public class WebhookBiFunctionTest {
	
	WebhookBiFunction webhookBiFunction;
	
	MockRestServiceServer restService;
	
	@Before
	public void setUp() throws Exception {
		this.webhookBiFunction = (WebhookBiFunction) FunctionMap.TYPE_FUNCTIONS.get(FunctionType.Webhook);
		this.restService = MockRestServiceServer
				.bindTo(this.webhookBiFunction.getRestTemplate())
				.ignoreExpectOrder(true)
				.bufferContent()
				.build();
	}
	
	@Test
	public void apply_GetBasicValue_ReturnsJson() throws Exception {
		// Setup
		String expected = "{ \"key1\": \"value1\" }";

		ComputeTree.Node webhookNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Webhook)
				.functionConfigs(Map.of(
						WebhookBiFunction.CONFIG_URL, new Value("/basicValue"),
						WebhookBiFunction.CONFIG_METHOD, new Value("GET")
				))
				.build();
		ComputeTree computeTree = ComputeTreeUtils.create(webhookNode);

		this.restService
				.expect(MockRestRequestMatchers.requestTo("/basicValue"))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(MockRestResponseCreators
						.withStatus(HttpStatus.ACCEPTED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(expected));

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(webhookNode.getId()).getValue();
		String actual = value.asString();
		Assert.assertEquals(expected, actual);

		// Verify
		this.restService.verify();
	}
	
	@Test
	public void apply_GetWithOutputPath_ReturnsSimpleValue() throws Exception {
		// Setup
		String response = "{ \"key1\": \"value1\" }";
		String expected = "value1";

		ComputeTree.Node webhookNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Webhook)
				.functionConfigs(Map.of(
						WebhookBiFunction.CONFIG_URL, new Value("/simpleValue"),
						WebhookBiFunction.CONFIG_METHOD, new Value("GET"),
						WebhookBiFunction.CONFIG_OUTPUT_PATH, new Value("key1")
				))
				.build();
		ComputeTree computeTree = ComputeTreeUtils.create(webhookNode);

		this.restService
				.expect(MockRestRequestMatchers.requestTo("/simpleValue"))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(MockRestResponseCreators
						.withStatus(HttpStatus.ACCEPTED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(response));

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(webhookNode.getId()).getValue();
		String actual = value.asString();
		Assert.assertEquals(expected, actual);

		// Verify
		this.restService.verify();
	}
	
	@Test
	public void apply_GetWithOutputPath_ReturnsComplexValue() throws Exception {
		// Setup
		Map<String, Object> response = Map.of(
				"key1", "value1",
				"key2", Map.of("nestedKey", "nestedValue")
		);
		Map<String, Object> expected = (Map) response.get("key2");

		ComputeTree.Node webhookNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Webhook)
				.functionConfigs(Map.of(
						WebhookBiFunction.CONFIG_URL, new Value("/complexValue"),
						WebhookBiFunction.CONFIG_METHOD, new Value("GET"),
						WebhookBiFunction.CONFIG_OUTPUT_PATH, new Value("key2")
				))
				.build();
		ComputeTree computeTree = ComputeTreeUtils.create(webhookNode);

		this.restService
				.expect(MockRestRequestMatchers.requestTo("/complexValue"))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(MockRestResponseCreators
						.withStatus(HttpStatus.ACCEPTED)
						.contentType(MediaType.APPLICATION_JSON)
						.body(new ObjectMapper().writeValueAsString(response)));

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(webhookNode.getId()).getValue();
		Map<String, Object> actual = value.asMap();
		Assert.assertEquals(expected, actual);

		// Verify
		this.restService.verify();
	}
	
}
