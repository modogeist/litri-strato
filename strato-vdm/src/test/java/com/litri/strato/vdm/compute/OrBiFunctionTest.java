package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.FunctionType;
import org.junit.Test;

public class OrBiFunctionTest extends BaseLogicalBiFunctionTest {
	
	@Test
	public void apply_TrueTrueNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.Or, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
	}
	
	@Test
	public void apply_TrueFalseNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.Or, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
	}
	
	@Test
	public void apply_FalseTrueNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.Or, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE);
	}
	
	@Test
	public void apply_FalseFalseNodes_ReturnFalseValue() throws Exception {
		this.apply(FunctionType.Or, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
	}
	
}
