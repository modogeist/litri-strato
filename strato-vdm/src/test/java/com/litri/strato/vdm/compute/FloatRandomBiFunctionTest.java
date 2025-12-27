package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.ValueType;
import java.util.Map;
import org.junit.Test;

public class FloatRandomBiFunctionTest extends BaseRandomBiFunctionTest {
	
	@Test
	public void apply_RandomNode_ReturnRandomValueType() throws Exception {
		this.apply(FunctionType.RandomFloat, Map.of(), ValueType.Float);
	}
	
}
