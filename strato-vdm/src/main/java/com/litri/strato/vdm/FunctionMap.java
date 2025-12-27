package com.litri.strato.vdm;

import static com.litri.strato.vdm.FunctionType.*;
import com.litri.strato.vdm.compute.*;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionMap {

	// Map.of(...) has limit of 10.
	public static final Map<FunctionType, BiFunction<TreeComputer.Context, UUID, Value>> TYPE_FUNCTIONS = Stream.of(
			// Value ops
			Map.<FunctionType, BiFunction>of(
					Constant, new ConstantBiFunction(),
					RandomBoolean, new BooleanRandomBiFunction(),
					RandomBoundedInteger, new BoundedIntegerRandomBiFunction(),
					RandomInteger, new IntegerRandomBiFunction(),
					RandomLong, new LongRandomBiFunction(),
					RandomFloat, new FloatRandomBiFunction(),
					RandomDouble, new DoubleRandomBiFunction(),
					Node, new NodeBiFunction(),
					Webhook, new WebhookBiFunction()),
			// Code ops
			Map.<FunctionType, BiFunction>of(
					Jexl, new JexlBiFunction()),
			// Filter ops
			Map.<FunctionType, BiFunction>of(
					Set, new SetBiFunction(),
					List, new ListBiFunction(),
					EqualsTo, new EqualsToBiFunction(),
					GreaterThan, new GreaterThanBiFunction(),
					LesserThan, new LesserThanBiFunction(),
					Mode, new ModeBiFunction()),
			// Logical ops
			Map.<FunctionType, BiFunction>of(
					And, new AndBiFunction(),
					Or, new OrBiFunction(),
					Not, new NotBiFunction(),
					IsEqual, new IsEqualBiFunction(),
					IsGreater, new IsGreaterBiFunction(),
					IsLesser, new IsLesserBiFunction()),
			// Mathematical ops
			Map.<FunctionType, BiFunction>of(
					Addition, new AdditionBiFunction(),
					Subtraction, new SubtractionBiFunction(),
					Multiplication, new MultiplicationBiFunction(),
					Division, new DivisionBiFunction(),
					Negation, new NegationBiFunction(),
					Absolution, new AbsolutionBiFunction()),
			// Aggregate ops
			Map.<FunctionType, BiFunction>of(
					Count, new CountBiFunction(),
					Sum, new SumBiFunction(),
					Minimum, new MinimumBiFunction(),
					Maximum, new MaximumBiFunction(),
					Mean, new MeanBiFunction(),
					Median, new MedianBiFunction()),
			// Empty ops
			Map.<FunctionType, BiFunction>of()
	)
			.flatMap(map -> map.entrySet().stream())
			.collect(Collectors.toMap(
					Map.Entry::getKey,
					Map.Entry::getValue));

}
