package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Set;

public class LongRandomBiFunction extends BaseRandomBiFunction {

	public LongRandomBiFunction() {
		super();
		this.outputTypes = Set.of(ValueType.Long);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		return new Value(this.random.nextLong());
	}

}
