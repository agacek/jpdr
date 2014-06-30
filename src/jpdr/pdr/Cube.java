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

	public Set<Var> getVars() {
		return concat(positives.stream(), negatives.stream()).collect(toSet());
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
		if (!(obj instanceof Cube)) {
			return false;
		}
		Cube other = (Cube) obj;
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
