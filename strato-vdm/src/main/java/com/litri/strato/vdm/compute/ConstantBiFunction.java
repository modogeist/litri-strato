package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Map;

public class ConstantBiFunction extends BaseBiFunction {

	public static final String CONFIG_VALUE = "value";
	
	public ConstantBiFunction() {
		this.configTypes = Map.of(CONFIG_VALUE, ValueType.SINGLES);
		this.inputMinCount = 0L;
		this.inputMaxCount = 0L;
		this.inputTypes = ValueType.NONE;
		this.outputTypes = ValueType.SINGLES;
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		Map<String, Value> configs = functionContext.getConfigs();
		Value constantValue = configs.get(CONFIG_VALUE);
		return new Value(constantValue.asObject());
	}

}
