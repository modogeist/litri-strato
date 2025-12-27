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

public class TreeSegmentorTest {
	
	@Test
	public void segment_SmallTree_Success() throws Exception {
		// Setup
		List<ComputeTree.Node> constantNodes = IntStream.range(0, 1000)
				.mapToObj(i -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(1)))
						.build())
				.collect(Collectors.toList());
		ComputeTree computeTree = ComputeTreeUtils.create(constantNodes, new int[] {100, 10, 5, 2, 1});
		
		UUID rootId = computeTree.getRootIds().iterator().next();
		ComputeTree.Node rootNode = computeTree.getIdNodes().get(rootId);
		ComputeTree.Node node = rootNode;
		for (int i = 0; i < 4; i++) {
			UUID nodeId = node.getHigherIds().iterator().next();
			node = computeTree.getIdNodes().get(nodeId);
		}

		// Execute
		TreeSegmentor treeSegmentor = TreeSegmentor.builder().build();
		Future<ComputeTree> future = treeSegmentor.segment(computeTree, node.getId());
		ComputeTree treeSegment = future.get();
		
		ValueTree valueTree = TreeComputer.builder().build().compute(treeSegment).get();
		UUID valueId = valueTree.getRootIds().iterator().next();
		ValueTree.Node valueNode = valueTree.getIdNodes().get(valueId);

		// Assert
		Assert.assertEquals(11, treeSegment.getIdNodes().size());
		Assert.assertEquals(1, treeSegment.getRootIds().size());
		Assert.assertEquals(10, treeSegment.getLeafIds().size());
		
		Assert.assertEquals(10, valueNode.getValue().asInteger().intValue());

		// Verify
	}

}
