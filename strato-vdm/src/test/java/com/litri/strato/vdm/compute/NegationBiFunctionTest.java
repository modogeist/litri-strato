package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.value.Value;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;

public class NegationBiFunctionTest {
	
	@Test
	public void apply_PositiveNodes_ReturnNegativeValue() throws Exception {
		// Setup
		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(9)))
				.build();
		ComputeTree.Node negationNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Negation)
				.build();
		Collections.addAll(constantNode.getLowerIds(), negationNode.getId());
		Collections.addAll(negationNode.getHigherIds(), constantNode.getId());

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode, negationNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(negationNode.getId()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(-9, actual.intValue());

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
		ComputeTree.Node negationNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Negation)
				.build();
		Collections.addAll(constantNode.getLowerIds(), negationNode.getId());
		Collections.addAll(negationNode.getHigherIds(), constantNode.getId());

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode, negationNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(negationNode.getId()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(9, actual.intValue());

		// Verify
	}
	
}
