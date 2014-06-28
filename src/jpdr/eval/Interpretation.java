package jpdr.eval;

import java.util.Map;

import jpdr.syntax.CNF;
import jpdr.syntax.Clause;
import jpdr.syntax.Cube;
import jpdr.syntax.Literal;

public class Interpretation {
	private final Map<String, Boolean> map;

	public Interpretation(Map<String, Boolean> map) {
		this.map = map;
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
