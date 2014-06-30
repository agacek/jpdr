package jpdr.eval;

import jpdr.expr.BinaryExpr;
import jpdr.expr.BoolExpr;
import jpdr.expr.ExprVisitor;
import jpdr.expr.NotExpr;
import jpdr.expr.Var;

public class TernaryEval implements ExprVisitor<Boolean> {
	private final Interpretation interp;

	public TernaryEval(Interpretation interp) {
		this.interp = interp;
	}

	@Override
	public Boolean visit(BinaryExpr e) {
		Boolean left = e.left.accept(this);
		Boolean right = e.right.accept(this);

		switch (e.op) {
		case AND:
			if (Boolean.FALSE.equals(left) || Boolean.FALSE.equals(right)) {
				return false;
			} else if (left == null || right == null) {
				return null;
			} else {
				return true;
			}
			
		case OR:
			if (Boolean.TRUE.equals(left) || Boolean.TRUE.equals(right)) {
				return true;
			} else if (left == null || right == null) {
				return null;
			} else {
				return false;
			}
			
		case XOR:
			if (left == null || right == null) {
				return null;
			} else {
				return left != right;
			}
			
		case EQUAL:
			if (left == null || right == null) {
				return null;
			} else {
				return left == right;
			}
			
		case IMPLIES:
			if (Boolean.FALSE.equals(left) || Boolean.TRUE.equals(right)) {
				return true;
			} else if (left == null || right == null) {
				return null;
			} else {
				return false;
			}
			
		default:
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Boolean visit(BoolExpr e) {
		return e.bool;
	}

	@Override
	public Boolean visit(NotExpr e) {
		Boolean v = e.expr.accept(this);
		if (v == null) {
			return null;
		} else {
			return !v;
		}
	}

	@Override
	public Boolean visit(Var e) {
		return interp.get(e);
	}
}
