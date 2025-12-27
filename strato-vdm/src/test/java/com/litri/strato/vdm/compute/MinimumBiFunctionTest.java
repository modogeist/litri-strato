package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

public class MinimumBiFunctionTest {
	
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
		ComputeTree.Node minimumNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Minimum)
				.build();
		minimumNode.addHighers(constantNodes.toArray(new ComputeTree.Node[0]));

		ComputeTree computeTree = ComputeTreeUtils.create(constantNodes.toArray(new ComputeTree.Node[0]));
		computeTree.add(minimumNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(minimumNode.getId()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
	@Test
	public void apply_0To2Nodes_Return0Value() throws Exception {
		// Setup
		Integer expected = 0;
		List<ComputeTree.Node> constantNodes = IntStream.range(0, 3)
				.mapToObj(i -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(i)))
						.build())
				.collect(Collectors.toList());
		ComputeTree.Node minimumNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Minimum)
				.build();
		minimumNode.addHighers(constantNodes.toArray(new ComputeTree.Node[0]));

		ComputeTree computeTree = ComputeTreeUtils.create(constantNodes.toArray(new ComputeTree.Node[0]));
		computeTree.add(minimumNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(minimumNode.getId()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
}
