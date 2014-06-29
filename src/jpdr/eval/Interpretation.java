package jpdr.eval;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jpdr.expr.Expr;
import jpdr.expr.Var;

public class Interpretation {
	public final static Interpretation EMPTY = new Interpretation(Collections.emptyMap());

	private final Map<Var, Boolean> map;

	public Interpretation(Map<Var, Boolean> map) {
		this.map = Collections.unmodifiableMap(map);
	}

	public Interpretation add(Var key, Boolean value) {
		HashMap<Var, Boolean> newMap = new HashMap<>(map);
		newMap.put(key, value);
		return new Interpretation(newMap);
	}

	public Boolean get(Var key) {
		return map.get(key);
	}

	public boolean eval(Expr e) {
		return e.accept(new Eval(this));
	}

	@Override
	public String toString() {
		return map.entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue())
				.collect(joining(", "));
	}
}
