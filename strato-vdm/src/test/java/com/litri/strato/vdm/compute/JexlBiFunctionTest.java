package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.junit.Assert;
import org.junit.Test;

public class JexlBiFunctionTest {

	@Test
	public void apply_SimpleStringExpression_ReturnsString() throws Exception {
		// Setup
		String expected = "hello world!";

		ComputeTree.Node jexlNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Jexl)
				.functionConfigs(Map.of(JexlBiFunction.CONFIG_EXPRESSION, new Value(String.format("'%s'", expected))))
				.build();
		ComputeTree computeTree = ComputeTreeUtils.create(jexlNode);

		// Execute
		Future<ValueTree> future = TreeComputer.builder().build().compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(jexlNode.getId()).getValue();
		String actual = value.asString();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
	@Test
	public void apply_SimpleMathExpression_ReturnsNumber() throws Exception {
		// Setup
		Double expected = 20.0;

		ComputeTree.Node jexlNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Jexl)
				.functionConfigs(Map.of(JexlBiFunction.CONFIG_EXPRESSION, new Value("(1 + 9) * 10 / 5.0")))
				.build();
		ComputeTree computeTree = ComputeTreeUtils.create(jexlNode);

		// Execute
		Future<ValueTree> future = TreeComputer.builder().build().compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(jexlNode.getId()).getValue();
		Double actual = value.asDouble();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
	@Test
	public void apply_ConstantNodeExpression_ReturnsConstant() throws Exception {
		// Setup
		String expected = "value from node";

		ComputeTree.Node constantNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.name("constantValue")
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(expected)))
				.build();
		ComputeTree.Node jexlNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Jexl)
				.functionConfigs(Map.of(JexlBiFunction.CONFIG_EXPRESSION, new Value(constantNode.getName())))
				.build();
		jexlNode.addHighers(constantNode);
		ComputeTree computeTree = ComputeTreeUtils.create(constantNode, jexlNode);

		// Execute
		Future<ValueTree> future = TreeComputer.builder().build().compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(jexlNode.getId()).getValue();
		String actual = value.asString();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
	@Test
	public void apply_MathNodeExpression_ReturnsNumber() throws Exception {
		// Setup
		Double expected = 100.0;

		ComputeTree.Node constantNode1 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.name("constant1")
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(20.0)))
				.build();
		ComputeTree.Node constantNode2 = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.name("constant2")
				.functionType(FunctionType.Constant)
				.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(5)))
				.build();
		ComputeTree.Node jexlNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(FunctionType.Jexl)
				.functionConfigs(Map.of(JexlBiFunction.CONFIG_EXPRESSION, new Value(String.format("%s * %s", constantNode1.getName(), constantNode2.getName()))))
				.build();
		jexlNode.addHighers(constantNode1, constantNode2);
		ComputeTree computeTree = ComputeTreeUtils.create(constantNode1, constantNode2, jexlNode);

		// Execute
		Future<ValueTree> future = TreeComputer.builder().build().compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(jexlNode.getId()).getValue();
		Double actual = value.asDouble();
		Assert.assertEquals(expected, actual);

		// Verify
	}
	
}
