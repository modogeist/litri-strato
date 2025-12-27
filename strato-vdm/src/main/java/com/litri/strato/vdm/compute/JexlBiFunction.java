package com.litri.strato.vdm.compute;

import com.litri.strato.vdm.ValueTree;
import com.litri.strato.vdm.ValueType;
import com.litri.strato.vdm.value.Value;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

public class JexlBiFunction extends BaseBiFunction {

	public static final String CONFIG_EXPRESSION = "expression";
	
	private final JexlEngine jexlEngine = new JexlBuilder().create();
	private final ConcurrentMap<String, JexlExpression> jexlTextExpressions = ExpiringMap.builder()
			.expiration(1, TimeUnit.MINUTES)
			.expirationPolicy(ExpirationPolicy.ACCESSED)
			.build();
	
	public JexlBiFunction() {
		this.configTypes = Map.of(CONFIG_EXPRESSION, Set.of(ValueType.String));
		this.inputMinCount = 0L;
		this.inputMaxCount = Long.MAX_VALUE;
		this.inputTypes = ValueType.ALL;
		this.outputTypes = ValueType.ALL;
	}
	
	@Override
	protected Value applyInternal(Context functionContext) {
		Map<String, Value> configs = functionContext.getConfigs();
		String expression = configs.get(CONFIG_EXPRESSION).asString();
		
		ValueTree.Node valueNode = functionContext.getValueNode().get();
		Map<String, Object> nodeNameValues = valueNode.getHigherIds().stream()
				.map(higherId -> functionContext.getValueNode(higherId))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(node -> node.getName(), node -> node.getValue().asObject()));
		
		JexlContext jexlContext = new MapContext(nodeNameValues);
		JexlExpression jexlExpression = this.getExpression(expression);
		Object value = jexlExpression.evaluate(jexlContext);
		
		return new Value(value);
	}
	
	private JexlExpression getExpression(String expression) {
		return this.jexlTextExpressions.computeIfAbsent(expression, (expr) -> this.jexlEngine.createExpression(expr));
	}

}
