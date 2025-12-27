package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueMap;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AdditionBiFunction extends BaseBiFunction {

	public AdditionBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 2L;
		this.inputMaxCount = 2L;
		this.inputTypes = ValueType.NUMBERS;
		this.outputTypes = ValueType.NUMBERS;
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Number number = values.stream()
				.map(value -> value.asDouble())
				.reduce(0.0, (t, u) -> t + u);

		List<ValueType> types = this.getTypes(values);
		ValueType highestPrec = ValueMap.getHighestPrecision(types);
		Function<Number, Number> highestMapper = ValueMap.TYPE_MAPPERS.get(highestPrec);
		Number precNumber = highestMapper.apply(number);
		return new Value(precNumber);
	}

}
