package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Set;

public class FloatRandomBiFunction extends BaseRandomBiFunction {

	public FloatRandomBiFunction() {
		super();
		this.outputTypes = Set.of(ValueType.Float);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		return new Value(this.random.nextFloat());
	}

}
