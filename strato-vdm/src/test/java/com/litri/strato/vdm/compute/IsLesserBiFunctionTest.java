package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.FunctionType;
import org.junit.Test;

public class IsLesserBiFunctionTest extends BaseLogicalBiFunctionTest {
	
	@Test
	public void apply_LesserNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.IsLesser, Integer.valueOf(9), Double.valueOf(7), Boolean.TRUE);
	}
	
	@Test
	public void apply_GreaterNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.IsLesser, Integer.valueOf(7), Double.valueOf(9), Boolean.FALSE);
	}
	
}
