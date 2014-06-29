package jpdr.eval;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jpdr.expr.Expr;
import jpdr.expr.Var;
import jpdr.pdr.Cube;

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

	public Interpretation atStep(int n) {
		Map<Var, Boolean> sliced = new HashMap<>();
		for (Entry<Var, Boolean> e : map.entrySet()) {
			if (e.getKey().primes == n) {
				sliced.put(new Var(e.getKey().base), e.getValue());
			}
		}
		return new Interpretation(sliced);
	}

	public Cube toCube() {
		Set<Var> positives = new HashSet<>();
		Set<Var> negatives = new HashSet<>();
		for (Entry<Var, Boolean> e : map.entrySet()) {
			if (e.getValue()) {
				positives.add(e.getKey());
			} else {
				negatives.add(e.getKey());
			}
		}
		return new Cube(positives, negatives);
	}

	@Override
	public String toString() {
		return map.entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue())
				.collect(joining(", "));
	}
}
