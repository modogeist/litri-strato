package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;

public class AdditionBiFunctionTest {
	
	@Test
	public void apply_ConstantNodes_ReturnSumValue() throws Exception {
		// Setup
		ComputeTree.Node constantNode1 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(9)))
				.build();
		ComputeTree.Node constantNode2 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(7)))
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

		// Assert
		Value value = valueTree.getIdNodes().get(additionNode.getId()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(9 + 7, actual.intValue());

		// Verify
	}
	
}
