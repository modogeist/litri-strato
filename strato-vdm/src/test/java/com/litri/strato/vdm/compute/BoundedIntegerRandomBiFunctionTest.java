package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class BoundedIntegerRandomBiFunctionTest extends BaseRandomBiFunctionTest {
	
	@Test
	public void apply_RandomNode_ReturnRandomValueType() throws Exception {
		Value value = this.apply(FunctionType.RandomBoundedInteger, Map.of(BoundedIntegerRandomBiFunction.CONFIG_BOUND, new Value(10)), ValueType.Integer);
		
		Assert.assertTrue(10 > value.asInteger());
	}
	
}
