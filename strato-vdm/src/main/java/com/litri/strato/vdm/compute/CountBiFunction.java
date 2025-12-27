package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CountBiFunction extends BaseBiFunction {

	public CountBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 0L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = ValueType.ALL;
		this.outputTypes = Set.of(ValueType.Long);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Long count = values.stream().count();
		return new Value(count);
	}

}
