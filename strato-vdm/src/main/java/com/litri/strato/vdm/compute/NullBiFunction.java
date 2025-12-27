package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.Set;

public class NullBiFunction extends BaseBiFunction {

	public NullBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 0L;
		this.inputMaxCount = 0L;
		this.inputTypes = ValueType.ALL;
		this.outputTypes = Set.of(ValueType.Void);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		return new Value(null);
	}

}
