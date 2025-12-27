package com.litri.strato.vdm;

import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * A concrete tree that tracks nodes, roots, leaves, and links to represent
 * valuational relationships of higher nodes to lower nodes
 */
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper=true)
public class ValueTree extends BaseTree<ValueTree, ValueTree.Node> {
	
	@Override
	protected ValueTree createTree(UUID uid, UUID id, Integer version, String name) {
		return ValueTree.builder()
				.uid(uid)
				.id(id)
				.version(version)
				.name(name)
				.build();
	}
	
	@Override
	protected ValueTree.Node createNode(UUID id, String name, ValueTree.Node node) {
		return ValueTree.Node.builder()
				.id(id)
				.name(name)
				.updateTime(node.getUpdateTime())
				.value(node.getValue())
				.build();
	}
	
	@SuperBuilder
	@NoArgsConstructor
	@ToString(callSuper=true)
	@Getter
	@Setter
	public static class Node extends BaseTree.Node {
		private volatile Value value;
	}

}
