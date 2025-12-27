package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.value.Value;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.junit.Assert;

public abstract class BaseLogicalBiFunctionTest {
	
	protected Value apply(FunctionType functionType, Object t, Object u, Boolean r) throws Exception {
		// Setup
		ComputeTree.Node constantNode1 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.name("node1")
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(t)))
				.build();
		ComputeTree.Node constantNode2 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(u)))
				.build();
		ComputeTree.Node logicalNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(functionType)
				.functionConfigs(Map.of("target", new Value(constantNode1.getName())))
				.build();
		logicalNode.addHighers(constantNode1, constantNode2);

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode1, constantNode2, logicalNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(logicalNode.getId()).getValue();
		Boolean actual = value.asBoolean();
		Assert.assertEquals(r, actual);

		// Verify

		return value;
	}
	
	protected Value apply(FunctionType functionType, Object t, List<Object> u, List<Object> r) throws Exception {
		// Setup
		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.name("node1")
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(t)))
				.build();
		List<ComputeTree.Node> nodes = u.stream()
				.map(obj -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(obj)))
						.build())
				.collect(Collectors.toList());
		ComputeTree.Node logicalNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(functionType)
				.functionConfigs(Map.of("target", new Value(constantNode.getName())))
				.build();
		logicalNode.addHighers(constantNode);
		logicalNode.addHighers(nodes.toArray(new ComputeTree.Node[0]));

		ComputeTree computeTree = ComputeTreeUtils.create(constantNode, logicalNode);
		computeTree.add(nodes.toArray(new ComputeTree.Node[0]));

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(logicalNode.getId()).getValue();
		List<Object> actual = value.asList();
		Comparator<Object> comparator = (o1, o2) -> o1.toString().compareTo(o2.toString());
		r = r.stream()
				.sorted(comparator)
				.collect(Collectors.toList());
		actual = actual.stream()
				.sorted(comparator)
				.collect(Collectors.toList());
		Assert.assertEquals(r, actual);

		// Verify

		return value;
	}
	
}
