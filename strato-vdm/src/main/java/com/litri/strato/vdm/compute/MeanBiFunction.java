package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;

public class MeanBiFunction extends BaseBiFunction {

	public MeanBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 1L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = ValueType.NUMBERS;
		this.outputTypes = ValueType.NUMBERS;
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Number number = values.stream()
				.map(value -> value.asDouble())
				.reduce(0.0, Double::sum);
		Number mean = number.doubleValue() / values.size();
		return new Value(mean);
	}

}
