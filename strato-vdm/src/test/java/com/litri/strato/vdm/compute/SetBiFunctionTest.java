package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;

public class SetBiFunctionTest {
	
	@Test
	public void apply_ConstantNodes_ReturnConstantValues() throws Exception {
		// Setup
		Integer constant = 9;
		ComputeTree.Node constantNode1 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(constant)))
				.build();
		ComputeTree.Node constantNode2 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(constant)))
				.build();
		ComputeTree.Node setNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Set)
				.build();
		setNode.addHighers(constantNode1, constantNode2);

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode1, constantNode2, setNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(setNode.getId()).getValue();
		Set actuals = value.asSet();
		Assert.assertEquals(1, actuals.size());
		Assert.assertTrue(actuals.contains(constant));

		// Verify
	}
	
}
