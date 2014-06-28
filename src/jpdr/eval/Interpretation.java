package jpdr.eval;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jpdr.syntax.CNF;
import jpdr.syntax.Clause;
import jpdr.syntax.Cube;
import jpdr.syntax.Literal;

public class Interpretation {
	public final static Interpretation EMPTY = new Interpretation(Collections.emptyMap());

	private final Map<String, Boolean> map;

	public Interpretation(Map<String, Boolean> map) {
		this.map = Collections.unmodifiableMap(map);
	}

	public Interpretation add(String key, Boolean value) {
		HashMap<String, Boolean> newMap = new HashMap<>(map);
		newMap.put(key, value);
		return new Interpretation(newMap);
	}

	@Override
	public String toString() {
		return map.entrySet().stream().map(e -> e.getKey() + " -> " + e.getValue())
				.collect(joining(", "));
	}

	public Boolean eval(Literal literal) {
		Boolean b = map.get(literal.var);
		if (b == null) {
			return null;
		}

		boolean val = b.booleanValue();
		return new Boolean(literal.positive ? val : !val);
	}

	public Boolean eval(Clause clause) {
		return clause.literals.stream().map(this::eval).reduce(false, Interpretation::or);
	}

	public Boolean eval(Cube cube) {
		return cube.literals.stream().map(this::eval).reduce(true, Interpretation::and);
	}

	public Boolean eval(CNF cnf) {
		return cnf.clauses.stream().map(this::eval).reduce(true, Interpretation::and);
	}

	private static Boolean and(Boolean a, Boolean b) {
		if (Boolean.FALSE.equals(a) || Boolean.FALSE.equals(b)) {
			return Boolean.FALSE;
		}

		if (a == null || b == null) {
			return null;
		} else {
			return Boolean.TRUE;
		}
	}

	private static Boolean or(Boolean a, Boolean b) {
		if (Boolean.TRUE.equals(a) || Boolean.TRUE.equals(b)) {
			return Boolean.TRUE;
		}

		if (a == null || b == null) {
			return null;
		} else {
			return Boolean.FALSE;
		}
	}
}
