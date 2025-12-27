package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

public class ModeBiFunctionTest {
	
	@Test
	public void apply_9Node_Return9Value() throws Exception {
		// Setup
		Integer expected = 9;
		List<ComputeTree.Node> constantNodes = IntStream.range(0, 1)
				.mapToObj(i -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(9)))
						.build())
				.collect(Collectors.toList());
		ComputeTree.Node modeNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Mode)
				.build();
		modeNode.addHighers(constantNodes.toArray(new ComputeTree.Node[0]));

		ComputeTree computeTree = ComputeTreeUtils.create(constantNodes.toArray(new ComputeTree.Node[0]));
		computeTree.add(modeNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(modeNode.getId()).getValue();
		Set actuals = value.asSet();
		Assert.assertEquals(1, actuals.size());
		Assert.assertTrue(actuals.contains(expected));

		// Verify
	}
	
	@Test
	public void apply_0To2Nodes_Return0To2Values() throws Exception {
		// Setup
		List<ComputeTree.Node> constantNodes = IntStream.range(0, 3)
				.mapToObj(i -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(i)))
						.build())
				.collect(Collectors.toList());
		ComputeTree.Node modeNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Mode)
				.build();
		modeNode.addHighers(constantNodes.toArray(new ComputeTree.Node[0]));

		ComputeTree computeTree = ComputeTreeUtils.create(constantNodes.toArray(new ComputeTree.Node[0]));
		computeTree.add(modeNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(modeNode.getId()).getValue();
		Set actuals = value.asSet();
		Assert.assertEquals(3, actuals.size());
		IntStream.range(0, 3)
				.forEach(i -> Assert.assertTrue(actuals.contains(i)));

		// Verify
	}
	
}
