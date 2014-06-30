package jpdr.pdr;

import static jpdr.expr.Expr.TRUE;
import static jpdr.expr.Expr.and;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jpdr.expr.Expr;

public class Frame {
	private final Expr expr;
	private final Set<Clause> clauses = new HashSet<>();

	public Frame(Expr expr) {
		this.expr = expr;
	}

	public Frame() {
		this.expr = TRUE;
	}

	public Expr toExpr() {
		return and(expr, and(clauses.stream().map(Clause::toExpr)));
	}

	public Set<Clause> getClauses() {
		return clauses;
	}

	public void addClause(Clause incoming) {
		Iterator<Clause> iter = clauses.iterator();
		while (iter.hasNext()) {
			Clause existing = iter.next();
			if (incoming.isSubclauseOf(existing) && !incoming.equals(existing)) {
				System.err.println("Replacing " + existing + " with " + incoming);
				iter.remove();
			}
		}
		clauses.add(incoming);
	}
	
	@Override
	public String toString() {
		return clauses.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clauses == null) ? 0 : clauses.hashCode());
		result = prime * result + ((expr == null) ? 0 : expr.hashCode());
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
		if (!(obj instanceof Frame)) {
			return false;
		}
		Frame other = (Frame) obj;
		if (clauses == null) {
			if (other.clauses != null) {
				return false;
			}
		} else if (!clauses.equals(other.clauses)) {
			return false;
		}
		if (expr == null) {
			if (other.expr != null) {
				return false;
			}
		} else if (!expr.equals(other.expr)) {
			return false;
		}
		return true;
	}
}
