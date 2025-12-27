package com.litri.strato.vdm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ComputeTreeUtils {
	
	public static ComputeTree create(ComputeTree.Node... nodes) {
		ComputeTree computeTree = ComputeTree.builder()
				.uid(UUID.randomUUID())
				.id(UUID.randomUUID())
				.version(0)
				.build();
		for (ComputeTree.Node node : nodes) {
			computeTree.add(node);
		}
		return computeTree;
	}

	public static ComputeTree create(List<ComputeTree.Node> constantNodes, int[] counts) {
		List<ComputeTree.Node> allNodes = new ArrayList();
		allNodes.addAll(constantNodes);
		
		List<ComputeTree.Node> higherNodes = null;
		List<ComputeTree.Node> lowerNodes = constantNodes;
		for (int count : counts) {
			higherNodes = lowerNodes;
			lowerNodes = new ArrayList();
			ComputeTree.Node lowerNode = null;
			int size = Math.ceilDiv(higherNodes.size(), count);
			for (int i = 0; i < higherNodes.size(); i++) {
				if (i % size == 0) {
					lowerNode = ComputeTree.Node.builder()
							.id(UUID.randomUUID())
							.name("Sum-" + i)
							.functionType(FunctionType.Sum)
							.build();
					lowerNodes.add(lowerNode);
					allNodes.add(lowerNode);
				}
				ComputeTree.Node higherNode = higherNodes.get(i);
				higherNode.addLowers(lowerNode);
			}
		}

		return create(allNodes.toArray(new ComputeTree.Node[0]));
	}
	
}
