package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueType;
import java.util.Map;
import java.util.Random;

public abstract class BaseRandomBiFunction extends BaseBiFunction {

	protected final Random random = new Random();
	
	public BaseRandomBiFunction() {
		this.configTypes = Map.of();
		this.inputMinCount = 0L;
		this.inputMaxCount = 0L;
		this.inputTypes = ValueType.NONE;
		this.outputTypes = ValueType.NONE;
	}

}
