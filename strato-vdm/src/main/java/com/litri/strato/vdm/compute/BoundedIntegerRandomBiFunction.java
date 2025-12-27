package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.Set;

public class BoundedIntegerRandomBiFunction extends BaseRandomBiFunction {

	public static final String CONFIG_BOUND = "bound";

	public BoundedIntegerRandomBiFunction() {
		super();
		this.configTypes = Map.of(CONFIG_BOUND, Set.of(ValueType.Integer));
		this.outputTypes = Set.of(ValueType.Integer);
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		Map<String, Value> configs = functionContext.getConfigs();
		Value bound = configs.get(CONFIG_BOUND);
		return new Value(this.random.nextInt(bound.asInteger()));
	}

}
