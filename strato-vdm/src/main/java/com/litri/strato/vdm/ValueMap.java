package com.litri.strato.vdm;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueMap {
	
	public static final Map<ValueType, Class> TYPE_CLASSES = Stream.of(
			// Basic types
			Map.<ValueType, Class>of(
					ValueType.Void, Void.class,
					ValueType.Boolean, Boolean.class,
					ValueType.Byte, Byte.class,
					ValueType.Short, Short.class,
					ValueType.Integer, Integer.class,
					ValueType.Long, Long.class,
					ValueType.Float, Float.class,
					ValueType.Double, Double.class,
					ValueType.String, String.class),
			// Other types
			Map.<ValueType, Class>of(
					ValueType.Uid, UUID.class,
					ValueType.Date, Date.class,
					ValueType.Duration, Duration.class,
					ValueType.List, List.class,
					ValueType.Set, Set.class,
					ValueType.Map, Map.class),
			// Empty types
			Map.<ValueType, Class>of()
	)
			.flatMap(map -> map.entrySet().stream())
			.collect(Collectors.toMap(
					Map.Entry::getKey,
					Map.Entry::getValue));

	public static final Map<ValueType, Function<Number, Number>> TYPE_MAPPERS = Stream.of(
			// Basic types
			Map.<ValueType, Function>of(
					ValueType.Byte, (t) -> Objects.isNull(t) ? null : ((Number) t).byteValue(),
					ValueType.Short, (t) -> Objects.isNull(t) ? null : ((Number) t).shortValue(),
					ValueType.Integer, (t) -> Objects.isNull(t) ? null : ((Number) t).intValue(),
					ValueType.Long, (t) -> Objects.isNull(t) ? null : ((Number) t).longValue(),
					ValueType.Float, (t) -> Objects.isNull(t) ? null : ((Number) t).floatValue(),
					ValueType.Double, (t) -> Objects.isNull(t) ? null : ((Number) t).doubleValue())
	)
			.flatMap(map -> map.entrySet().stream())
			.collect(Collectors.toMap(
					Map.Entry::getKey,
					Map.Entry::getValue));

	public static ValueType getHighestPrecision(List<ValueType> types) {
		for (ValueType type : ValueType.PRECISION_ORDER) {
			if (types.contains(type)) {
				return type;
			}
		}
		return null;
	}

}
