package jpdr.expr;

import java.util.HashSet;
import java.util.Set;

public class BinaryExpr implements Expr {
	public final Expr left;
	public final BinaryOp op;
	public final Expr right;

	public BinaryExpr(Expr left, BinaryOp op, Expr right) {
		this.left = left;
		this.op = op;
		this.right = right;
	}

	@Override
	public BinaryExpr prime(int n) {
		return new BinaryExpr(left.prime(n), op, right.prime(n));
	}
	
	@Override
	public Set<Var> getVars() {
		HashSet<Var> set = new HashSet<>();
		set.addAll(left.getVars());
		set.addAll(right.getVars());
		return set;
	}

	@Override
	public String toString() {
		return "(" + left + " " + op + " " + right + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((op == null) ? 0 : op.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		if (!(obj instanceof BinaryExpr)) {
			return false;
		}
		BinaryExpr other = (BinaryExpr) obj;
		if (left == null) {
			if (other.left != null) {
				return false;
			}
		} else if (!left.equals(other.left)) {
			return false;
		}
		if (op != other.op) {
			return false;
		}
		if (right == null) {
			if (other.right != null) {
				return false;
			}
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
