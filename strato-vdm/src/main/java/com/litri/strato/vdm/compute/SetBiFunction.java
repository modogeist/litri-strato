package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SetBiFunction extends BaseBiFunction {

	public SetBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 1L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = ValueType.SINGLES;
		this.outputTypes = Set.of(ValueType.Set);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Set set = values.stream()
				.map(value -> value.asObject())
				.collect(Collectors.toSet());
		return new Value(set);
	}

}