package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class IsEqualBiFunction extends BaseBiFunction {

	public IsEqualBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 2L;
		this.inputMaxCount = 2L;
		this.inputTypes = ValueType.SINGLES;
		this.outputTypes = Set.of(ValueType.Boolean);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		List<Value> values = functionContext.getValues();
		Iterator<Value> itr = values.iterator();
		Value value1 = itr.next();
		Value value2 = itr.next();
		Boolean bool = ValueType.NUMBERS.contains(value1.getType()) && ValueType.NUMBERS.contains(value2.getType())
				? Objects.equals(value1.asDouble(), value2.asDouble())
				: Objects.equals(value1.asObject(), value2.asObject());
		return new Value(bool);
	}

}
