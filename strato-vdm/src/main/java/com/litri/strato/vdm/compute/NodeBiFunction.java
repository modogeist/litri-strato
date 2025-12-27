package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class NodeBiFunction extends BaseBiFunction {

	public static final String CONFIG_TREE_ID = "treeId";
	public static final String CONFIG_TREE_VERSION = "treeVersion";
	public static final String CONFIG_NODE_ID = "nodeId";

	public NodeBiFunction() {
		this.configTypes = Map.of(
				CONFIG_TREE_ID, Set.of(ValueType.Uid),
				CONFIG_TREE_VERSION, Set.of(ValueType.Integer),
				CONFIG_NODE_ID, Set.of(ValueType.Uid));
		this.inputMinCount = 0L;
		this.inputMaxCount = 0L;
		this.inputTypes = ValueType.NONE;
		this.outputTypes = ValueType.ALL;
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		Map<String, Value> configs = functionContext.getConfigs();
		Value treeIdValue = configs.get(CONFIG_TREE_ID);
		Value treeVersionValue = configs.get(CONFIG_TREE_VERSION);
		Value nodeIdValue = configs.get(CONFIG_NODE_ID);
		
		UUID treeId = treeIdValue.asUid();
		Integer treeVersion = treeVersionValue.asInteger();
		UUID nodeId = nodeIdValue.asUid();
		Optional<ValueTree.Node> valueNode = functionContext.getAuxValueNode(treeId, treeVersion, nodeId);
		return valueNode.map(ValueTree.Node::getValue).orElse(null);
	}

}
