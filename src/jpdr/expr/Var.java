package jpdr.expr;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.Set;
import java.util.stream.IntStream;

public class Var implements Expr {
	public final String base;
	public final int primes;

	public Var(String base, int primes) {
		this.base = base;
		this.primes = primes;
	}

	public Var(String base) {
		this(base, 0);
	}

	@Override
	public Var prime(int n) {
		return new Var(base, primes + n);
	}
	
	@Override
	public Set<Var> getVars() {
		return Collections.singleton(this);
	}

	@Override
	public String toString() {
		return base + IntStream.range(0, primes).mapToObj(i -> "'").collect(joining());
	}

	@Override
	public int hashCode() {
		return 31 * base.hashCode() + Integer.hashCode(primes);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Var) {
			Var other = (Var) obj;
			return base.equals(other.base) && primes == other.primes;
		}
		return false;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
