package com.litri.strato.vdm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.vdm.compute.ConstantBiFunction;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ComputeTreeTest {
	
	@Test
	public void convertJson_ToJsonFromJson_Success() throws Exception {
		// Setup
		ComputeTree.Node constantNode1 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(9)))
				.build();
		ComputeTree.Node constantNode2 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(9)))
				.build();
		ComputeTree.Node additionNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Addition)
				.build();
		additionNode.addHighers(constantNode1, constantNode2);

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode1, constantNode2, additionNode);

		// Execute
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(computeTree);
		ComputeTree actual = objectMapper.readValue(json, ComputeTree.class);

		// Assert
		Assert.assertEquals(computeTree, actual);
		
		// Verify
	}
	
}
