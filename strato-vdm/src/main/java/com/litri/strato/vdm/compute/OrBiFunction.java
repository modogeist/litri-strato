package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class OrBiFunction extends BaseBiFunction {

	public OrBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 1L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = Set.of(ValueType.Boolean);
		this.outputTypes = Set.of(ValueType.Boolean);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Boolean bool = values.stream()
				.map(value -> value.asBoolean())
				.anyMatch(val -> Objects.equals(val, Boolean.TRUE));
		return new Value(bool);
	}

}
