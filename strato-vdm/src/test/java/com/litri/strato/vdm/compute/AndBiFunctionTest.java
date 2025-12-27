package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.FunctionType;
import org.junit.Test;

public class AndBiFunctionTest extends BaseLogicalBiFunctionTest {
	
	@Test
	public void apply_TrueTrueNodes_ReturnTrueValue() throws Exception {
		this.apply(FunctionType.And, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
	}
	
	@Test
	public void apply_TrueFalseNodes_ReturnFalseValue() throws Exception {
		this.apply(FunctionType.And, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE);
	}
	
	@Test
	public void apply_FalseTrueNodes_ReturnFalseValue() throws Exception {
		this.apply(FunctionType.And, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
	}
	
	@Test
	public void apply_FalseFalseNodes_ReturnFalseValue() throws Exception {
		this.apply(FunctionType.And, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
	}
	
}
