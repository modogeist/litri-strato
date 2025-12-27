package com.litri.strato.vdm;

import com.litri.strato.vdm.compute.ConstantBiFunction;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

public class TreeComputerTest {
	
	@Test
	public void compute_LotsaNodes_ReturnTotalValue() throws Exception {
		// Setup
		List<ComputeTree.Node> constantNodes = IntStream.range(0, 1000)
				.mapToObj(i -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(i)))
						.build())
				.collect(Collectors.toList());
		ComputeTree computeTree = ComputeTreeUtils.create(constantNodes, new int[] {100, 10, 5, 2, 1});

		Integer expected = 0;
		for (ComputeTree.Node node : constantNodes) {
			expected += node.getFunctionConfigs().get(ConstantBiFunction.CONFIG_VALUE).asInteger();
		}

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(valueTree.getRootIds().iterator().next()).getValue();
		Integer actual = value.asInteger();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
}
