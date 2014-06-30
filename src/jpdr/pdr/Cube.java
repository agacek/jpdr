package jpdr.pdr;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static jpdr.expr.Expr.and;
import static jpdr.expr.Expr.not;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import jpdr.eval.Interpretation;
import jpdr.expr.Expr;
import jpdr.expr.Var;

public class Cube {
	public final Set<Var> positives;
	public final Set<Var> negatives;

	public Cube(Set<Var> positives, Set<Var> negatives) {
		this.positives = Collections.unmodifiableSet(positives);
		this.negatives = Collections.unmodifiableSet(negatives);
	}

	public Cube(Stream<Var> positives, Stream<Var> negatives) {
		this(positives.collect(toSet()), negatives.collect(toSet()));
	}

	public Expr toExpr() {
		return and(concat(positives.stream(), negatives.stream().map(v -> not(v))));
	}
	
	public Clause negate() {
		return new Clause(negatives, positives);
	}

	public Cube prime() {
		return new Cube(positives.stream().map(Var::prime), negatives.stream().map(Var::prime));
	}
	
	public Interpretation toInterpretation() {
		Map<Var, Boolean> map = new HashMap<>();
		positives.stream().forEach(v -> map.put(v, true));
		negatives.stream().forEach(v -> map.put(v, false));
		return new Interpretation(map);
	}
	
	@Override
	public String toString() {
		return toExpr().toString();
	}
}
