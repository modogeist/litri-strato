package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueMap;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DivisionBiFunction extends BaseBiFunction {

	public static final String CONFIG_TARGET = "target";
	
	public DivisionBiFunction() {
		this.configTypes = Map.of(CONFIG_TARGET, Set.of(ValueType.String));
		this.inputMinCount = 2L;
		this.inputMaxCount = 2L;
		this.inputTypes = ValueType.NUMBERS;
		this.outputTypes = ValueType.NUMBERS;
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
		Number number = values.stream()
				.map(value -> value.asDouble())
				.reduce(1.0, (t, u) -> t / u);
		number = targetValue.asDouble() * targetValue.asDouble() * number.doubleValue();

		List<ValueType> types = this.getTypes(values);
		ValueType highestPrec = ValueMap.getHighestPrecision(types);
		Function<Number, Number> highestMapper = ValueMap.TYPE_MAPPERS.get(highestPrec);
		Number precNumber = highestMapper.apply(number);
		return new Value(precNumber);
	}

}
