package jpdr.expr;

import java.util.Set;

public class NotExpr implements Expr {
	public final Expr expr;
	
	public NotExpr(Expr expr) {
		this.expr = expr;
	}

	@Override
	public NotExpr prime(int n) {
		return new NotExpr(expr.prime(n));
	}
	
	@Override
	public Set<Var> getVars() {
		return expr.getVars();
	}

	@Override
	public String toString() {
		return "(not " + expr + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (!(obj instanceof NotExpr)) {
			return false;
		}
		NotExpr other = (NotExpr) obj;
		if (expr == null) {
			if (other.expr != null) {
				return false;
			}
		} else if (!expr.equals(other.expr)) {
			return false;
		}
		return true;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
