package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueMap;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModeBiFunction extends BaseBiFunction {

	public ModeBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 1L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = ValueType.SINGLES;
		this.outputTypes = Set.of(ValueType.Set);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Map<Number, Long> valueCounts = values.stream()
				.collect(Collectors.groupingBy(Value::asDouble, Collectors.counting()));
		Long maxCount = valueCounts.values().stream()
				.max(Long::compare)
				.get();
		Set<Number> numbers = valueCounts.entrySet().stream()
				.filter(entry -> Objects.equals(entry.getValue(), maxCount))
				.map(entry -> entry.getKey())
				.collect(Collectors.toSet());
				
		List<ValueType> types = this.getTypes(values);
		ValueType highestPrec = ValueMap.getHighestPrecision(types);
		Function<Number, Number> highestMapper = ValueMap.TYPE_MAPPERS.get(highestPrec);
		Set<Number> precNumbers = numbers.stream()
				.map(number -> highestMapper.apply(number))
				.collect(Collectors.toSet());
		return new Value(precNumbers);
	}

}
