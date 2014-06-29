package jpdr.expr;

import java.util.Collections;
import java.util.Set;

public class BoolExpr implements Expr {
	public final boolean bool;

	public BoolExpr(boolean bool) {
		this.bool = bool;
	}
	
	@Override
	public BoolExpr prime(int n) {
		return this;
	}
	
	@Override
	public Set<Var> getVars() {
		return Collections.emptySet();
	}

	@Override
	public String toString() {
		return Boolean.toString(bool);
	}

	@Override
	public int hashCode() {
		return Boolean.hashCode(bool);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BoolExpr) {
			BoolExpr other = (BoolExpr) obj;
			return bool == other.bool;
		}
		return false;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
