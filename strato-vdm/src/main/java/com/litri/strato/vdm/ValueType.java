package com.litri.strato.vdm;

public enum ValueType {
	
	// Basic types
	Void,
	Boolean,
	Byte,
	Short,
	Integer,
	Long,
	Float,
	Double,
	String,
	
	// Other types
	Uid,
	Date,
	Duration,
	List,
	Set,
	Map;
	
	public static final java.util.Set<ValueType> NONE = java.util.Set.of();
	public static final java.util.Set<ValueType> ALL = java.util.Set.of(
			Boolean,
			Byte,
			Short,
			Integer,
			Long,
			Float,
			Double,
			String,
			Uid,
			Date,
			Duration,
			List,
			Set,
			Map);
	public static final java.util.Set<ValueType> SINGLES = java.util.Set.of(
			Boolean,
			Byte,
			Short,
			Integer,
			Long,
			Float,
			Double,
			String,
			Uid,
			Date,
			Duration	);
	public static final java.util.Set<ValueType> NUMBERS = java.util.Set.of(
			Byte,
			Short,
			Integer,
			Long,
			Float,
			Double);
	
	public static final java.util.List<ValueType> PRECISION_ORDER = java.util.List.of(
			Double,
			Float,
			Long,
			Integer,
			Short,
			Byte);
	
}
