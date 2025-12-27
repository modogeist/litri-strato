package com.litri.strato.vdm.value;

import com.litri.strato.vdm.ValueType;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;

public class ValueTest {
	
	@Test
	public void ctor_BooleanObject_ReturnBooleanValue() {
		this.test(ValueType.Boolean, Boolean.TRUE);
	}
	
	@Test
	public void ctor_ByteObject_ReturnByteValue() {
		this.test(ValueType.Byte, (byte) 0);
	}
	
	@Test
	public void ctor_ShortObject_ReturnShortValue() {
		this.test(ValueType.Short, (short) 0);
	}
	
	@Test
	public void ctor_IntegerObject_ReturnIntegerValue() {
		this.test(ValueType.Integer, 0);
	}
	
	@Test
	public void ctor_LongObject_ReturnLongValue() {
		this.test(ValueType.Long, (long) 0);
	}
	
	@Test
	public void ctor_FloatObject_ReturnFloatValue() {
		this.test(ValueType.Float, (float) 0);
	}
	
	@Test
	public void ctor_DoubleObject_ReturnDoubleValue() {
		this.test(ValueType.Double, (double) 0);
	}
	
	@Test
	public void ctor_StringObject_ReturnStringValue() {
		this.test(ValueType.String, "string");
	}
	
	@Test
	public void ctor_UidObject_ReturnUidValue() {
		this.test(ValueType.Uid, UUID.randomUUID());
	}
	
	@Test
	public void ctor_DateObject_ReturnDateValue() {
		this.test(ValueType.Date, new Date());
	}
	
	@Test
	public void ctor_DurationObject_ReturnDurationValue() {
		this.test(ValueType.Duration, Duration.ofDays(0));
	}
	
	@Test
	public void ctor_ListObject_ReturnListValue() {
		this.test(ValueType.List, List.of());
	}
	
	@Test
	public void ctor_SetObject_ReturnSetValue() {
		this.test(ValueType.Set, Set.of());
	}
	
	private void test(ValueType valueType, Object object) {
		// Setup
		
		// Execute
		Value value = new Value(object);
		
		// Assert
		Assert.assertEquals(valueType, value.getType());
		Assert.assertEquals(object.getClass(), value.asObject().getClass());
		
		// Verify
	}
	
}
