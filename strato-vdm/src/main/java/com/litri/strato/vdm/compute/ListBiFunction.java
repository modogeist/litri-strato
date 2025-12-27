package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ListBiFunction extends BaseBiFunction {
	
	public ListBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 1L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = ValueType.SINGLES;
		this.outputTypes = Set.of(ValueType.List);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		List list = values.stream()
				.map(value -> value.asObject())
				.collect(Collectors.toList());
		return new Value(list);
	}

}