package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * An abstraction that validates configurations, inputs, and outputs.  Real calculations are performed by subclasses.
 */
@EqualsAndHashCode
@ToString
@Getter
public abstract class BaseBiFunction implements BiFunction<TreeComputer.Context, UUID, Value> {
	
	@NonNull
	protected Map<String, Set<ValueType>> configTypes;
	@NonNull
	protected Long inputMinCount;
	@NonNull
	protected Long inputMaxCount;
	@NonNull
	protected Set<ValueType> inputTypes;
	@NonNull
	protected Set<ValueType> outputTypes;
	
	@Override
	public Value apply(TreeComputer.Context treeContext, UUID nodeId) {
		ComputeTree.Node computeNode = treeContext.getComputeTree().getIdNodes().get(nodeId);
		Map<String, Value> configs = computeNode.getFunctionConfigs();
		if (!this.configTypes.isEmpty()) {
			this.validateConfigs(configs);
		}
		
		ValueTree.Node valueNode = treeContext.getValueTree().getIdNodes().get(nodeId);
		List<Value> values = valueNode.getHigherIds().stream()
				.map(higherId -> treeContext.getValueTree().getIdNodes().get(higherId))
				.map(node -> node.getValue())
				.collect(Collectors.toList());
		if (!this.inputTypes.isEmpty()) {
			this.validateInputs(values);
		}
		
		BaseBiFunction.Context functionContext = BaseBiFunction.Context.builder()
				.treeContext(treeContext)
				.nodeId(nodeId)
				.configs(configs)
				.values(values)
				.build();
		Value value = this.applyInternal(functionContext);
		this.validateOutput(value);
		
		return value;
	}
	
	protected List<ValueType> getTypes(List<Value> values) {
		return values.stream()
				.map(value -> value.getType())
				.collect(Collectors.toList());
	}
	
	protected void validateConfigs(Map<String, Value> configs) {
		boolean notValid = !this.configTypes.entrySet().stream()
				.allMatch(entry -> {
					String acceptedName = entry.getKey();
					Set<ValueType> acceptedTypes = entry.getValue();
					return configs.containsKey(acceptedName) && acceptedTypes.contains(configs.get(acceptedName).getType());
				});
		if (notValid) {
			throw new IllegalArgumentException("Configurations not valid");
		}
	}
	
	protected void validateInputs(List<Value> values) {
		boolean notValid = this.inputMinCount > values.size();
		if (notValid) {
			throw new IllegalArgumentException("Input min count not valid");
		}				
		notValid = this.inputMaxCount < values.size();
		if (notValid) {
			throw new IllegalArgumentException("Input max count not valid");
		}
		notValid = values.stream()
				.map(value -> value.getType())
				.map(type -> this.inputTypes.contains(type))
				.anyMatch(bool -> Objects.equals(bool, Boolean.FALSE));
		if (notValid) {
			throw new IllegalArgumentException("Input type not valid");
		}
	}
	
	protected void validateOutput(Value value) {
		boolean notValid = !this.outputTypes.contains(value.getType());
		if (notValid) {
			throw new IllegalArgumentException("Output type not valid");
		}
	}
	
	protected abstract Value applyInternal(Context functionContext);
	
	@Builder
	public static class Context {
		@NonNull
		private TreeComputer.Context treeContext;
		@NonNull
		private UUID nodeId;
		
		@Getter
		private Map<String, Value> configs;
		@Getter
		private List<Value> values;
		
		public Optional<ValueTree.Node> getValueNode() {
			return this.getValueNode(this.nodeId);
		}
		
		public Optional<ValueTree.Node> getValueNode(String name) {
			return this.getValueNode()
					.map(node -> node.getHigherIds())
					.orElse(Collections.emptySet())
					.stream()
					.map(higherId -> this.getValueNode(higherId))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(node -> Objects.equals(node.getName(), name))
					.findFirst();
		}
		
		public Optional<ValueTree.Node> getValueNode(UUID nodeId) {
			return Optional.ofNullable(this.treeContext.getValueTree().getIdNodes().get(nodeId));
		}
		
		public Optional<ValueTree.Node> getAuxValueNode(UUID treeId, Integer treeVersion, UUID nodeId) {
			TreeComputer.TreeRef treeRef = TreeComputer.TreeRef.builder()
					.id(treeId)
					.version(treeVersion)
					.build();
			return Optional.ofNullable(this.treeContext.getAuxValueTrees().get(treeRef))
					.map(tree -> tree.getIdNodes().get(nodeId));
		}
	}
	
}
