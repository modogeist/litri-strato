package com.litri.strato.vdm.value;

import com.litri.strato.vdm.ValueType;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Value {
	
	@Getter
	private ValueType type;
	@Getter
	private Object value;
	
	public Value(Object value) {
		this.value = value;
		if (value == null) {
			this.type = ValueType.Void;
		} else if (value instanceof Boolean) {
			this.type = ValueType.Boolean;
		} else if (value instanceof Byte) {
			this.type = ValueType.Byte;
		} else if (value instanceof Short) {
			this.type = ValueType.Short;
		} else if (value instanceof Integer) {
			this.type = ValueType.Integer;
		} else if (value instanceof Long) {
			this.type = ValueType.Long;
		} else if (value instanceof Float) {
			this.type = ValueType.Float;
		} else if (value instanceof Double) {
			this.type = ValueType.Double;
		} else if (value instanceof String) {
			this.type = ValueType.String;
		} else if (value instanceof UUID) {
			this.type = ValueType.Uid;
		} else if (value instanceof Date) {
			this.type = ValueType.Date;
		} else if (value instanceof Duration) {
			this.type = ValueType.Duration;
		} else if (value instanceof List) {
			this.type = ValueType.List;
		} else if (value instanceof Set) {
			this.type = ValueType.Set;
		} else if (value instanceof Map) {
			this.type = ValueType.Map;
		}
	}
	
	public Value(ValueType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public Object asObject() {
		return this.value;
	}
	
	public Boolean asBoolean() {
		return Boolean.class.cast(this.value);
	}
	
	public Byte asByte() {
		return Objects.isNull(this.value)
				? null
				: Number.class.cast(this.value).byteValue();
	}
	
	public Short asShort() {
		return Objects.isNull(this.value)
				? null
				: Number.class.cast(this.value).shortValue();
	}
	
	public Integer asInteger() {
		return Objects.isNull(this.value)
				? null
				: Number.class.cast(this.value).intValue();
	}
	
	public Long asLong() {
		return Objects.isNull(this.value)
				? null
				: Number.class.cast(this.value).longValue();
	}
	
	public Float asFloat() {
		return Objects.isNull(this.value)
				? null
				: Number.class.cast(this.value).floatValue();
	}
	
	public Double asDouble() {
		return Objects.isNull(this.value)
				? null
				: Number.class.cast(this.value).doubleValue();
	}
	
	public Number asNumber() {
		return Number.class.cast(this.value);
	}
	
	public String asString() {
		return String.class.cast(this.value);
	}
	
	public UUID asUid() {
		return UUID.class.cast(this.value);
	}
	
	public Date asDate() {
		return Date.class.cast(this.value);
	}
	
	public Duration asDuration() {
		return Duration.class.cast(this.value);
	}
	
	public List asList() {
		return List.class.cast(this.value);
	}
	
	public Set asSet() {
		return Set.class.cast(this.value);
	}
	
	public Map asMap() {
		return Map.class.cast(this.value);
	}
	
}
