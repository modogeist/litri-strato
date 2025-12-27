package com.litri.strato.vdm;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * An abstract tree that tracks nodes, roots, leaves, and links.
 */
@SuperBuilder
@NoArgsConstructor
@ToString
@Getter
public abstract class BaseTree<T extends BaseTree, N extends BaseTree.Node> {

	@NonNull
	private UUID uid;
	@NonNull
	private UUID id;
	@NonNull
	@Setter
	private Integer version;
	
	@Setter
	private String name;
	@Setter
	private Date updateTime;

	@Builder.Default
	private Map<UUID, N> idNodes = new ConcurrentHashMap();
	@Builder.Default
	private Set<UUID> rootIds = ConcurrentHashMap.newKeySet();
	@Builder.Default
	private Set<UUID> leafIds = ConcurrentHashMap.newKeySet();

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 43 * hash + Objects.hashCode(this.id);
		hash = 43 * hash + Objects.hashCode(this.version);
		hash = 43 * hash + Objects.hashCode(this.name);
		// TODO: Should this be used?
		hash = 43 * hash + Objects.hashCode(this.updateTime);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BaseTree<?, ?> other = (BaseTree<?, ?>) obj;
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (!Objects.equals(this.version, other.version)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		// TODO: Should this be used?
		return Objects.equals(this.updateTime, other.updateTime);
	}
	
	protected abstract T createTree(UUID uid, UUID id, Integer version, String name);
	protected abstract N createNode(UUID uid, String name, N node);

	public void add(N... nodes) {
		for (N node : nodes) {
			UUID nodeId = node.getId();
			this.getIdNodes().putIfAbsent(nodeId, node);
			if (node.getLowerIds().isEmpty()) {
				this.getRootIds().add(nodeId);
			}
			if (node.getHigherIds().isEmpty()) {
				this.getLeafIds().add(nodeId);
			}
		}
	}

	public void update(N... nodes) {
		for (N node : nodes) {
			UUID nodeId = node.getId();
			if (node.getLowerIds().isEmpty()) {
				this.getRootIds().add(nodeId);
			} else {
				this.getRootIds().remove(nodeId);
			}
			if (node.getHigherIds().isEmpty()) {
				this.getLeafIds().add(nodeId);
			} else {
				this.getLeafIds().remove(nodeId);
			}
		}
	}

	public void remove(N... nodes) {
		for (N node : nodes) {
			UUID nodeId = node.getId();
			this.getIdNodes().remove(nodeId);
			this.getRootIds().remove(nodeId);
			this.getLeafIds().remove(nodeId);
		}
	}

	@SuperBuilder
	@NoArgsConstructor
	@ToString
	@Getter
	public static abstract class Node {
		@NonNull
		private UUID id;
	
		@Setter
		private String name;
		@Setter
		private Date updateTime;
		
		@Builder.Default
		private Set<UUID> lowerIds = ConcurrentHashMap.newKeySet();
		@Builder.Default
		private Set<UUID> higherIds = ConcurrentHashMap.newKeySet();

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 79 * hash + Objects.hashCode(this.id);
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Node other = (Node) obj;
			return Objects.equals(this.id, other.id);
		}
		
		public void addLowers(Node... nodes) {
			for (Node node : nodes) {
				this.lowerIds.add(node.getId());
				node.getHigherIds().add(this.getId());
			}
		}
		
		public void removeLowers(Node... nodes) {
			for (Node node : nodes) {
				this.lowerIds.remove(node.getId());
				node.getHigherIds().remove(this.getId());
			}
		}
		
		public void addHighers(Node... nodes) {
			for (Node node : nodes) {
				this.higherIds.add(node.getId());
				node.getLowerIds().add(this.getId());
			}
		}
		
		public void removeHighers(Node... nodes) {
			for (Node node : nodes) {
				this.higherIds.remove(node.getId());
				node.getLowerIds().remove(this.getId());
			}
		}
	}

}
