package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NotBiFunction extends BaseBiFunction {

	public NotBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 1L;
		this.inputMaxCount = 1L;
		this.inputTypes = Set.of(ValueType.Boolean);
		this.outputTypes = Set.of(ValueType.Boolean);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Boolean bool = !values.iterator().next().asBoolean();
		return new Value(bool);
	}

}
