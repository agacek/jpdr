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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((negatives == null) ? 0 : negatives.hashCode());
		result = prime * result + ((positives == null) ? 0 : positives.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Clause)) {
			return false;
		}
		Clause other = (Clause) obj;
		if (negatives == null) {
			if (other.negatives != null) {
				return false;
			}
		} else if (!negatives.equals(other.negatives)) {
			return false;
		}
		if (positives == null) {
			if (other.positives != null) {
				return false;
			}
		} else if (!positives.equals(other.positives)) {
			return false;
		}
		return true;
	}
}
