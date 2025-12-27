package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Set;

public class DoubleRandomBiFunction extends BaseRandomBiFunction {

	public DoubleRandomBiFunction() {
		super();
		this.outputTypes = Set.of(ValueType.Double);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		return new Value(this.random.nextDouble());
	}

}
