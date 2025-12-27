package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MedianBiFunction extends BaseBiFunction {

	public MedianBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 1L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = ValueType.NUMBERS;
		this.outputTypes = ValueType.NUMBERS;
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		List<Number> sortedValues = values.stream()
				.sorted((o1, o2) -> Double.compare(o1.asDouble(), o2.asDouble()))
				.map(Value::asNumber)
				.collect(Collectors.toList());
		int midIdx = Double.valueOf(Math.ceil(values.size() / 2.0F)).intValue() - 1;
		Number median = sortedValues.get((int) midIdx);
		return new Value(median);
	}

}
