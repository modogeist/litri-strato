package com.litri.strato.vdm;

public enum FunctionType {
	
	// Value ops
	Constant,
	RandomBoolean,
	RandomBoundedInteger,
	RandomInteger,
	RandomLong,
	RandomFloat,
	RandomDouble,
	Node,
	Webhook,
	
	// Code ops
	Jexl,
	
	// Filter ops
	Set,
	List,
	EqualsTo,
	GreaterThan,
	LesserThan,
	Mode,
	
	// Logical ops
	And,
	Or,
	Xor,
	Not,
	IsEqual,
	IsGreater,
	IsLesser,
	
	// Mathematical ops
	Addition,
	Subtraction,
	Multiplication,
	Division,
	Negation,
	Absolution,
	
	// Aggregate ops
	Count,
	Sum,
	Minimum,
	Maximum,
	Mean,
	Median;
	
}
