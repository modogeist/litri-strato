package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.BaseTree;
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

public class NodeBiFunctionTest {

	@Test
	public void apply_2TreesDiffValues_ReturnNotEqualValue() throws Exception {
		// Setup
		Boolean expected = false;

		// Tree1
		List<ComputeTree.Node> constantNodes = IntStream.range(0, 10)
				.mapToObj(i -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(i)))
						.build())
				.collect(Collectors.toList());
		ComputeTree.Node sumNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Sum)
				.build();
		sumNode.addHighers(constantNodes.toArray(new BaseTree.Node[0]));
		ComputeTree computeTree1 = ComputeTreeUtils.create(constantNodes.toArray(new ComputeTree.Node[0]));
		computeTree1.add(sumNode);

		ValueTree valueTree1 = TreeComputer.builder().build().compute(computeTree1).get();

		// Tree2
		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(9)))
				.build();
		ComputeTree.Node nodeNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Node)
				.functionConfigs(Map.of(
						NodeBiFunction.CONFIG_TREE_ID, new Value(valueTree1.getId()),
						NodeBiFunction.CONFIG_TREE_VERSION, new Value(valueTree1.getVersion()),
						NodeBiFunction.CONFIG_NODE_ID, new Value(sumNode.getId())))
				.build();
		ComputeTree.Node isEqualNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.IsEqual)
				.build();
		isEqualNode.addHighers(constantNode, nodeNode);
		ComputeTree computeTree2 = ComputeTreeUtils.create(constantNode, nodeNode, isEqualNode);

		ValueTree valueTree2 = TreeComputer.builder().build().prepare(computeTree2).get();

		// Execute
		Future<ValueTree> future = TreeComputer.builder().build().compute(computeTree2, valueTree2, Set.of(valueTree1));
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(isEqualNode.getId()).getValue();
		Boolean actual = value.asBoolean();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
	@Test
	public void apply_2TreesSameValues_ReturnEqualValue() throws Exception {
		// Setup
		Boolean expected = true;

		// Tree1
		List<ComputeTree.Node> constantNodes = IntStream.range(0, 10)
				.mapToObj(i -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(i)))
						.build())
				.collect(Collectors.toList());
		ComputeTree.Node sumNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Sum)
				.build();
		sumNode.addHighers(constantNodes.toArray(new BaseTree.Node[0]));
		ComputeTree computeTree1 = ComputeTreeUtils.create(constantNodes.toArray(new ComputeTree.Node[0]));
		computeTree1.add(sumNode);

		ValueTree valueTree1 = TreeComputer.builder().build().compute(computeTree1).get();

		// Tree2
		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(45)))
				.build();
		ComputeTree.Node nodeNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Node)
				.functionConfigs(Map.of(
						NodeBiFunction.CONFIG_TREE_ID, new Value(valueTree1.getId()),
						NodeBiFunction.CONFIG_TREE_VERSION, new Value(valueTree1.getVersion()),
						NodeBiFunction.CONFIG_NODE_ID, new Value(sumNode.getId())))
				.build();
		ComputeTree.Node isEqualNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.IsEqual)
				.build();
		isEqualNode.addHighers(constantNode, nodeNode);
		ComputeTree computeTree2 = ComputeTreeUtils.create(constantNode, nodeNode, isEqualNode);

		ValueTree valueTree2 = TreeComputer.builder().build().prepare(computeTree2).get();

		// Execute
		Future<ValueTree> future = TreeComputer.builder().build().compute(computeTree2, valueTree2, Set.of(valueTree1));
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(isEqualNode.getId()).getValue();
		Boolean actual = value.asBoolean();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
}
