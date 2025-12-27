package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Set;

public class BooleanRandomBiFunction extends BaseRandomBiFunction {

	public BooleanRandomBiFunction() {
		super();
		this.outputTypes = Set.of(ValueType.Boolean);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		return new Value(this.random.nextBoolean());
	}

}
