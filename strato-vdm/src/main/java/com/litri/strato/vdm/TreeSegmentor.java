package com.litri.strato.vdm;

import com.litri.strato.common.concurrency.DirectExecutorService;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * A basic utility that creates segments from trees using specified parameters.
 */
@Slf4j
@Builder
public class TreeSegmentor {
	
	@Builder.Default
	private ExecutorService executor = new DirectExecutorService();
	
	public <T extends BaseTree<T, N>, N extends BaseTree.Node> Future<T> segment(T mainTree, UUID nodeId) {
		if (Objects.isNull(mainTree)) {
			throw new IllegalArgumentException("Main tree is null");
		}
		
		Callable<T> task = () -> {
			log.debug("Starting segment for Tree(id={}, version={}, name={})", mainTree.getId(), mainTree.getVersion(), mainTree.getName());
			T segmentTree = mainTree.createTree(UUID.randomUUID(), UUID.randomUUID(), 1, mainTree.getName() + "-segment");
			TreeSegmentor.Context<T, N> treeContext = TreeSegmentor.Context.<T, N>builder()
					.mainTree(mainTree)
					.segmentTree(segmentTree)
					.nodeId(nodeId)
					.build();
			this.target(treeContext);
			this.segment(treeContext);
			
			if (Objects.nonNull(treeContext.getMainTree().getUpdateTime())) {
				treeContext.getSegmentTree().setUpdateTime(treeContext.getMainTree().getUpdateTime());
			}

			log.debug("Finishing segment for Tree(id={}, version={}, name={})", mainTree.getId(), mainTree.getVersion(), mainTree.getName());
			return segmentTree;
		};
		
		Future<T> future = this.executor.submit(task);
		return future;
	}
	
	private <T extends BaseTree<T, N>, N extends BaseTree.Node> void target(Context<T, N> treeContext) {
		log.trace("Targeting node {}", treeContext.getNodeId());
		this.target(treeContext, treeContext.getNodeId());
	}
	
	private <T extends BaseTree<T, N>, N extends BaseTree.Node> void target(Context<T, N> treeContext, UUID nodeId) {
		if (!treeContext.getMainTree().getIdNodes().containsKey(nodeId)) {
			throw new IllegalArgumentException(String.format("Tree node %s not found", nodeId));
		}
		
		treeContext.getTargetIds().add(nodeId);
		N node = treeContext.getMainTree().getIdNodes().get(nodeId);
		node.getHigherIds().parallelStream()
				.forEach(higherId -> this.target(treeContext, higherId));		
	}
	
	private <T extends BaseTree<T, N>, N extends BaseTree.Node> void segment(Context<T, N> treeContext) {
		log.trace("Segmenting node {}", treeContext.getNodeId());
		this.segment(treeContext, treeContext.getNodeId());
	}
	
	private <T extends BaseTree<T, N>, N extends BaseTree.Node> void segment(Context<T, N> treeContext, UUID nodeId) {
		Boolean isVisited = treeContext.getVisitedIds().putIfAbsent(nodeId, Boolean.TRUE);
		if (Objects.nonNull(isVisited)) {
			return;
		}
		if (!treeContext.getMainTree().getIdNodes().containsKey(nodeId)) {
			throw new IllegalArgumentException(String.format("Tree node %s not found", nodeId));
		}
		
		N origNode = treeContext.getMainTree().getIdNodes().get(nodeId);
		N copyNode = treeContext.getMainTree().createNode(origNode.getId(), origNode.getName(), origNode);
		if (!treeContext.getSegmentTree().getRootIds().isEmpty()) {
			origNode.getLowerIds().stream()
					.filter(lowerId -> treeContext.targetIds.contains(lowerId))
					.forEach(lowerId -> copyNode.getLowerIds().add(lowerId));
		}
		copyNode.getHigherIds().addAll(origNode.getHigherIds());		
		
		treeContext.getSegmentTree().add(copyNode);
		
		origNode.getHigherIds().parallelStream()
				.forEach(higherId -> this.segment(treeContext, higherId));
	}
	
	@Builder
	@Getter
	public static class Context<T extends BaseTree, N extends BaseTree.Node> {
		@NonNull
		private T mainTree;
		@NonNull
		private T segmentTree;
		@NonNull
		private UUID nodeId;
		
		@Builder.Default
		private Set<UUID> targetIds = ConcurrentHashMap.newKeySet();

		@Builder.Default
		private ConcurrentMap<UUID, Boolean> visitedIds = new ConcurrentHashMap();
	}
	
}
