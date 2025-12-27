package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueMap;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class NegationBiFunction extends BaseBiFunction {

	public NegationBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 1L;
		this.inputMaxCount = 1L;
		this.inputTypes = ValueType.NUMBERS;
		this.outputTypes = ValueType.NUMBERS;
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Number number = -values.iterator().next().asDouble();

		List<ValueType> types = this.getTypes(values);
		ValueType highestPrec = ValueMap.getHighestPrecision(types);
		Function<Number, Number> highestMapper = ValueMap.TYPE_MAPPERS.get(highestPrec);
		Number precNumber = highestMapper.apply(number);
		return new Value(precNumber);
	}

}
