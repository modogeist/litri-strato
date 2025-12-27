package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.FunctionType;
import org.junit.Test;

public class IsEqualsBiFunctionTest extends BaseLogicalBiFunctionTest {
	
	@Test
	public void apply_SameNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.IsEqual, Integer.valueOf(9), Double.valueOf(9), Boolean.TRUE);
	}
	
	@Test
	public void apply_DiffNodes_ReturnFalseValue() throws Exception {
		this.apply(FunctionType.IsEqual, Integer.valueOf(9), Double.valueOf(7), Boolean.FALSE);
	}
	
}
