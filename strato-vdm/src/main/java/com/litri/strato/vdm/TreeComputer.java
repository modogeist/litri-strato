package com.litri.strato.vdm;

import com.litri.strato.vdm.value.Value;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * A basic processor that takes compute trees and generates value trees, which
 * may/not be complete (some nodes may have null values).  Partially complete
 * value trees can be processed where left off by passing same trees again to
 * compute method.
 */
@Slf4j
@Builder
public class TreeComputer {
	
	@Builder.Default
	private ExecutorService executor = new ForkJoinPool(16, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
	
	public Future<ValueTree> prepare(ComputeTree computeTree) {
		if (Objects.isNull(computeTree)) {
			throw new IllegalArgumentException("Compute tree is null");
		}
		
		Callable<ValueTree> task = () -> {
			log.debug("Starting prepare for Tree(id={}, version={}, name={})", computeTree.getId(), computeTree.getVersion(), computeTree.getName());
			ValueTree valueTree = ValueTree.builder()
					.uid(UUID.randomUUID())
					.id(computeTree.getId())
					.version(computeTree.getVersion())
					.name(computeTree.getName())
					.build();
			TreeComputer.Context treeContext = TreeComputer.Context.builder()
					.computeTree(computeTree)
					.valueTree(valueTree)
					.build();
			treeContext.getComputeTree().getRootIds().parallelStream()
					.forEach(rootId -> this.prepare(treeContext, rootId));
			
			log.debug("Finishing prepare for Tree(id={}, version={}, name={})", computeTree.getId(), computeTree.getVersion(), computeTree.getName());
			return valueTree;
		};
		
		Future<ValueTree> future = this.executor.submit(task);
		return future;
	}
	
	private void prepare(Context treeContext, UUID nodeId) {
		log.trace("Preparing node {}", nodeId);
		
		Boolean isVisited = treeContext.getVisitedIds().putIfAbsent(nodeId, Boolean.TRUE);
		if (Objects.nonNull(isVisited)) {
			return;
		}
		if (!treeContext.getComputeTree().getIdNodes().containsKey(nodeId)) {
			throw new IllegalArgumentException(String.format("Compute node %s not found", nodeId));
		}
		
		ComputeTree.Node computeNode = treeContext.getComputeTree().getIdNodes().get(nodeId);
		ValueTree.Node valueNode = ValueTree.Node.builder()
				.id(computeNode.getId())
				.name(computeNode.getName())
				.build();
		valueNode.getLowerIds().addAll(computeNode.getLowerIds());
		valueNode.getHigherIds().addAll(computeNode.getHigherIds());
		
		treeContext.getValueTree().add(valueNode);
		
		computeNode.getHigherIds().parallelStream()
				.forEach(higherId -> this.prepare(treeContext, higherId));
	}
	
	public Future<ValueTree> compute(ComputeTree computeTree) throws Exception {
		Future<ValueTree> valueTree = this.prepare(computeTree);
		valueTree = this.compute(computeTree, valueTree.get());
		return valueTree;
	}
	
	public Future<ValueTree> compute(ComputeTree computeTree, ValueTree valueTree) {
		return this.compute(computeTree, valueTree, Set.of());
	}
	
	public Future<ValueTree> compute(ComputeTree computeTree, ValueTree valueTree, Collection<ValueTree> auxValueTrees) {
		if (Objects.isNull(valueTree)) {
			throw new IllegalArgumentException("Value tree null");
		}
		if (Objects.isNull(computeTree)) {
			throw new IllegalArgumentException("Compute tree null");
		}
		if (!Objects.equals(computeTree.getId(), valueTree.getId())) {
			throw new IllegalArgumentException("Tree ids different");
		}
		if (!Objects.equals(computeTree.getVersion(), valueTree.getVersion())) {
			throw new IllegalArgumentException("Tree versions different");
		}
		
		Callable<ValueTree> task = () -> {
			log.debug("Starting compute for Tree(id={}, version={}, name={})", computeTree.getId(), computeTree.getVersion(), computeTree.getName());
			Context treeContext = Context.builder()
					.computeTree(computeTree)
					.valueTree(valueTree)
					.auxValueTrees(this.map(auxValueTrees))
					.build();		
			treeContext.getValueTree().getRootIds().parallelStream()
					.forEach(rootId -> this.compute(treeContext, rootId));
			
			treeContext.getValueTree().setUpdateTime(new Date());
			log.debug("Moved {} times for Tree(id={}, version={}, name={})", treeContext.getMoves().intValue(), computeTree.getId(), computeTree.getVersion(), computeTree.getName());
			log.debug("Computed {} times for Tree(id={}, version={}, name={})", treeContext.getComputes().intValue(), computeTree.getId(), computeTree.getVersion(), computeTree.getName());

			log.debug("Finishing compute for Tree(id={}, version={}, name={})", computeTree.getId(), computeTree.getVersion(), computeTree.getName());
			return valueTree;
		};
		
		Future<ValueTree> future = this.executor.submit(task);
		return future;
	}
	
	private void compute(Context treeContext, UUID nodeId) {
		log.trace("Computing node {}", nodeId);
		
		treeContext.move();
		
		Boolean isVisited = treeContext.getVisitedIds().putIfAbsent(nodeId, Boolean.TRUE);
		if (Objects.nonNull(isVisited)) {
			return;
		}
		if (!treeContext.getValueTree().getIdNodes().containsKey(nodeId)) {
			throw new IllegalStateException(String.format("Value node %s not found", nodeId));
		}
		if (!treeContext.getComputeTree().getIdNodes().containsKey(nodeId)) {
			throw new IllegalStateException(String.format("Compute node %s not found", nodeId));
		}
			
		// Check for value.
		ValueTree.Node valueNode = treeContext.getValueTree().getIdNodes().get(nodeId);
		if (Objects.nonNull(valueNode.getValue())) {
			return;
		}
		
		// Check for inputs.
		for (UUID higherId : valueNode.getHigherIds()) {
			if (treeContext.getVisitedIds().containsKey(higherId)) {
				continue;
			}
			ValueTree.Node higherNode = treeContext.getValueTree().getIdNodes().get(higherId);
			if (Objects.isNull(higherNode.getValue())) {
				this.compute(treeContext, higherId);
			}
		}
		// Wait for inputs.
		for (UUID higherId : valueNode.getHigherIds()) {
			ValueTree.Node higherNode = treeContext.getValueTree().getIdNodes().get(higherId);
			synchronized (higherNode) {
				if (Objects.isNull(higherNode.getValue())) {
					try {
						higherNode.wait();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						log.error("Value node {} not ready", higherId);
						return;
					}
				}
				if (Objects.isNull(higherNode.getValue())) {
					log.warn("Value node {} not ready", higherId);
					return;
				}
			}
		}		
		
		// Compute value.
		this.computeValue(treeContext, nodeId);
	}
	
	private void computeValue(Context treeContext, UUID nodeId) {
		ComputeTree.Node computeNode = treeContext.getComputeTree().getIdNodes().get(nodeId);
		FunctionType functionType = computeNode.getFunctionType();
		BiFunction<Context, UUID, Value> biFunction = FunctionMap.TYPE_FUNCTIONS.get(functionType);
		
		treeContext.compute();
		
		Value value = biFunction.apply(treeContext, nodeId);
		if (Objects.isNull(value)) {
			log.warn("Node value {} is still null", nodeId);
		}
		
		ValueTree.Node valueNode = treeContext.getValueTree().getIdNodes().get(nodeId);
		synchronized (valueNode) {
			valueNode.setValue(value);
			valueNode.setUpdateTime(new Date());
			valueNode.notifyAll();
		}
	}
	
	private Map<TreeRef, ValueTree> map(Collection<ValueTree> valueTrees) {
		return valueTrees.stream()
				.collect(Collectors.toMap(
						valueTree -> TreeRef.builder().id(valueTree.getId()).version(valueTree.getVersion()).build(),
						valueTree -> valueTree));
	}
	
	@Builder
	@Getter
	public static class Context {
		@NonNull
		private ComputeTree computeTree;
		@NonNull
		private ValueTree valueTree;
		@Builder.Default
		private Map<TreeRef, ValueTree> auxValueTrees = new ConcurrentHashMap();

		@Builder.Default
		private ConcurrentMap<UUID, Boolean> visitedIds = new ConcurrentHashMap();
		
		@Builder.Default
		private AtomicInteger moves = new AtomicInteger();
		
		public void move() {
			this.moves.getAndIncrement();
		}
		
		@Builder.Default
		private AtomicInteger computes = new AtomicInteger();
		
		public void compute() {
			this.computes.getAndIncrement();
		}
	}
	
	@Builder
	@EqualsAndHashCode
	@Getter
	public static class TreeRef {
		private UUID id;
		private Integer version;
	}
	
}
