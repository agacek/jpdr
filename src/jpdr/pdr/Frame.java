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
			if (incoming.isSubclauseOf(existing)) {
				System.err.println("Replacing " + existing + " with " + incoming);
				iter.remove();
			}
		}
		clauses.add(incoming);
	}
}
