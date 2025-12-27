package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.FunctionType;
import java.util.List;
import org.junit.Test;

public class EqualsToBiFunctionTest extends BaseLogicalBiFunctionTest {
	
	@Test(expected = Exception.class)
	public void apply_NoNodes_ThrowException() throws Exception {
		this.apply(FunctionType.EqualsTo, 9, List.of(), List.of());
	}
	
	@Test
	public void apply_2MatchNodes_Return2Values() throws Exception {
		this.apply(FunctionType.EqualsTo, 9, List.of(8, 9.0, 9L, 10L), List.of(9.0, 9L));
	}
	
	@Test
	public void apply_0MatchNodes_Return0Values() throws Exception {
		this.apply(FunctionType.EqualsTo, Long.valueOf(9), List.of(8, 10L), List.of());
	}
	
}
