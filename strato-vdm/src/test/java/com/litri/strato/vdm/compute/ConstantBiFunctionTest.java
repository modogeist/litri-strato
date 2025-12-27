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

public class ConstantBiFunctionTest {
	
	@Test
	public void apply_ConstantNode_ReturnConstantValue() throws Exception {
		// Setup
		Integer expected = 9;
		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(expected)))
				.build();

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(constantNode.getId()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
}
