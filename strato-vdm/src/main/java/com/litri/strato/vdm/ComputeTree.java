package com.litri.strato.vdm;

import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * A concrete tree that tracks nodes, roots, leaves, and links to represent
 * computational relationships of higher nodes to lower nodes
 */
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper=true)
public class ComputeTree extends BaseTree<ComputeTree, ComputeTree.Node> {
	
	@Override
	protected ComputeTree createTree(UUID uid, UUID id, Integer version, String name) {
		return ComputeTree.builder()
				.uid(uid)
				.id(id)
				.version(version)
				.name(name)
				.build();
	}
	
	@Override
	protected ComputeTree.Node createNode(UUID id, String name, ComputeTree.Node node) {
		return ComputeTree.Node.builder()
				.id(id)
				.name(name)
				.updateTime(node.getUpdateTime())
				.functionType(node.getFunctionType())
				.functionConfigs(Objects.isNull(node.getFunctionConfigs()) ? null : Map.copyOf(node.getFunctionConfigs()))
				.build();
	}
	
	@SuperBuilder
	@NoArgsConstructor
	@ToString(callSuper=true)
	@Getter
	@Setter
	public static class Node extends BaseTree.Node {
		private FunctionType functionType;
		private Map<String, Value> functionConfigs;
	}

}
