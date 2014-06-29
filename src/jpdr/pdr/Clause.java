package jpdr.pdr;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;
import static jpdr.expr.Expr.not;
import static jpdr.expr.Expr.or;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import jpdr.expr.Expr;
import jpdr.expr.Var;

public class Clause {
	public final Set<Var> positives;
	public final Set<Var> negatives;

	public Clause(Set<Var> positives, Set<Var> negatives) {
		this.positives = Collections.unmodifiableSet(positives);
		this.negatives = Collections.unmodifiableSet(negatives);
	}

	public Clause(Stream<Var> positives, Stream<Var> negatives) {
		this(positives.collect(toSet()), negatives.collect(toSet()));
	}

	public Expr toExpr() {
		return or(concat(positives.stream(), negatives.stream().map(v -> not(v))));
	}

	public Cube negate() {
		return new Cube(negatives, positives);
	}

	public Clause prime() {
		return new Clause(positives.stream().map(Var::prime), negatives.stream().map(Var::prime));
	}

	@Override
	public String toString() {
		return toExpr().toString();
	}

	public boolean isSubclauseOf(Clause other) {
		return positives.stream().allMatch(other.positives::contains)
				&& negatives.stream().allMatch(other.negatives::contains);
	}

	public Clause remove(Var var) {
		return new Clause(positives.stream().filter(var::equals), negatives.stream().filter(
				var::equals));
	}
}
