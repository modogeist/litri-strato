package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ComputeTree;
import com.litri.strato.vdm.ComputeTreeUtils;
import com.litri.strato.vdm.FunctionType;
import com.litri.strato.vdm.TreeComputer;
import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.junit.Assert;

public class BaseRandomBiFunctionTest {
	
	protected Value apply(FunctionType functionType, Map<String, Value> functionConfigs, ValueType valueType) throws Exception {
		// Setup
		ComputeTree.Node randomNode = ComputeTree.Node.builder()
				.id(UUID.randomUUID())
				.functionType(functionType)
				.functionConfigs(functionConfigs)
				.build();

		ComputeTree computeTree = ComputeTreeUtils.create(randomNode);

		// Execute
		TreeComputer treeComputer = TreeComputer.builder().build();
		Future<ValueTree> future = treeComputer.compute(computeTree);
		ValueTree valueTree = future.get();

		// Assert
		Value value = valueTree.getIdNodes().get(randomNode.getId()).getValue();
		ValueType actual = value.getType();
		Assert.assertEquals(valueType, actual);

		// Verify

		return value;
	}
	
}
