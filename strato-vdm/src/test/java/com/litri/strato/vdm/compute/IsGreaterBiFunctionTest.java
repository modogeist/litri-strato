package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.FunctionType;
import org.junit.Test;

public class IsGreaterBiFunctionTest extends BaseLogicalBiFunctionTest {
	
	@Test
	public void apply_GreaterNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.IsGreater, Integer.valueOf(7), Double.valueOf(9), Boolean.TRUE);
	}
	
	@Test
	public void apply_LesserNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.IsGreater, Integer.valueOf(9), Double.valueOf(7), Boolean.FALSE);
	}
	
}
