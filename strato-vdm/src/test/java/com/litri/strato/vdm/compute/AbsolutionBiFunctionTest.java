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

public class AbsolutionBiFunctionTest {
	
	@Test
	public void apply_PositiveNodes_ReturnPositiveValue() throws Exception {
		// Setup
		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(9)))
				.build();
		ComputeTree.Node absolutionNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Absolution)
				.build();
		absolutionNode.addHighers(constantNode);

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode, absolutionNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(absolutionNode.getId()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(9, actual.intValue());

		// Verify
	}
	
	@Test
	public void apply_NegativeNodes_ReturnPositiveValue() throws Exception {
		// Setup
		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(-9)))
				.build();
		ComputeTree.Node absolutionNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Absolution)
				.build();
		absolutionNode.addHighers(constantNode);

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode, absolutionNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(absolutionNode.getId()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(9, actual.intValue());

		// Verify
	}
	
}
