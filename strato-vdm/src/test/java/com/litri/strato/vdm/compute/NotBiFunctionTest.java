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

public class NotBiFunctionTest {
	
	@Test
	public void apply_TrueNodes_ReturnFalseValue() throws Exception {
		this.apply(Boolean.TRUE, Boolean.FALSE);
	}
	
	@Test
	public void apply_FalseNodes_ReturnTrueValue() throws Exception {
		this.apply(Boolean.FALSE, Boolean.TRUE);
	}
	
	private void apply(Boolean t, Boolean r) throws Exception {
		// Setup
		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(t)))
				.build();
		ComputeTree.Node notNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Not)
				.build();
		Collections.addAll(constantNode.getLowerIds(), notNode.getId());
		Collections.addAll(notNode.getHigherIds(), constantNode.getId());

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode, notNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(notNode.getId()).getValue();
		Boolean actual = value.asBoolean();
		Assert.assertEquals(r, actual);

		// Verify
	}
	
}
