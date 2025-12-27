package com.litri.strato.vdm;

import com.litri.strato.vdm.compute.ConstantBiFunction;
import com.litri.strato.vdm.value.Value;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public class ComputeTreeUtilsTest {
	
	@Test
	public void create() throws Exception {
		// Setup
		List<ComputeTree.Node> constantNodes = IntStream.range(0, 100)
				.mapToObj(i -> ComputeTree.Node.builder()
						.id(UUID.randomUUID())
						.name(Integer.toString(i))
						.functionType(FunctionType.Constant)
						.functionConfigs(Map.of(ConstantBiFunction.CONFIG_VALUE, new Value(1)))
						.build())
				.collect(Collectors.toList());
		ComputeTree computeTree = ComputeTreeUtils.create(constantNodes, new int[] {10, 5, 2, 1});
		
		UUID rootId = computeTree.getRootIds().iterator().next();
		ComputeTree.Node computeNode = computeTree.getIdNodes().get(rootId);
		
		List<BaseTree.Node> computeNodes = List.of(computeNode);
		this.print(computeTree.getIdNodes(), computeNodes, (node) -> System.out.print(((ComputeTree.Node) node).getName() + " "));
				
		TreeComputer treeComputer = TreeComputer.builder().build();
		ValueTree valueTree = treeComputer.compute(computeTree).get();
		
		rootId = valueTree.getRootIds().iterator().next();
		ValueTree.Node valueNode = valueTree.getIdNodes().get(rootId);

		List<ValueTree.Node> valueNodes = List.of(valueNode);
		this.print(valueTree.getIdNodes(), valueNodes, (node) -> System.out.print(((ValueTree.Node) node).getValue().asInteger() + " "));
	}
	
	private void print(Map<UUID, ? extends BaseTree.Node> idNodes, List<? extends BaseTree.Node> nodes, Consumer<BaseTree.Node> printer) {
		for (int i = 0;; i++) {
			if (nodes.isEmpty()) {
				break;
			}
			System.out.println("######## " + i + " ########");
			nodes.stream().forEach(node -> printer.accept(node));
			nodes = nodes.stream()
					.map(node -> node.getHigherIds())
					.flatMap(Set::stream)
					.map(id -> idNodes.get(id))
					.collect(Collectors.toList());
			System.out.println();
		}
	}
	
}
