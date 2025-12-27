package com.litri.strato.vdm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litri.strato.vdm.compute.ConstantBiFunction;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;

public class ValueTreeTest {
	
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
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(valueTree);
		ValueTree actual = objectMapper.readValue(json, ValueTree.class);

		// Assert
		Assert.assertEquals(valueTree, actual);

		// Verify
	}
	
}
