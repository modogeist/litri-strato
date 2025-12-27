package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreaterThanBiFunction extends BaseBiFunction {
	
	public static final String CONFIG_TARGET = "target";

	public GreaterThanBiFunction() {
		this.configTypes = Map.of(CONFIG_TARGET, Set.of(ValueType.String));
		this.inputMinCount = 2L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = ValueType.NUMBERS;
		this.outputTypes = Set.of(ValueType.List);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		Map<String, Value> configs = functionContext.getConfigs();
		String targetName = configs.get(CONFIG_TARGET).asString();
		Optional<ValueTree.Node> targetValueNode = functionContext.getValueNode(targetName);
		if (targetValueNode.isEmpty()) {
			log.error("No node matching target name {}", targetName);
			return null;
		}
		
		Value targetValue = targetValueNode.get().getValue();
		List<Value> values = functionContext.getValues();
		List matches = values.stream()
				.filter(value -> value.asDouble() > targetValue.asDouble())
				.map(val -> val.asObject())
				.collect(Collectors.toList());
		return new Value(matches);
	}

}