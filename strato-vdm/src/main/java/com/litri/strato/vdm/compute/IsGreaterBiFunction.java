package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IsGreaterBiFunction extends BaseBiFunction {

	public static final String CONFIG_TARGET = "target";
	
	public IsGreaterBiFunction() {
		this.configTypes = Map.of(CONFIG_TARGET, Set.of(ValueType.String));
		this.inputMinCount = 2L;
		this.inputMaxCount = 2L;
		this.inputTypes = ValueType.NUMBERS;
		this.outputTypes = Set.of(ValueType.Boolean);
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
		Iterator<Value> itr = values.iterator();
		Value value = itr.next();
		if (Objects.equals(value, targetValue)) {
			value = itr.next();
		}
		Boolean bool = value.asDouble() > targetValue.asDouble();
		return new Value(bool);
	}

}
