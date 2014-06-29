package jpdr.eval;

import jpdr.expr.BinaryExpr;
import jpdr.expr.BoolExpr;
import jpdr.expr.ExprVisitor;
import jpdr.expr.NotExpr;
import jpdr.expr.Var;

public class Eval implements ExprVisitor<Boolean> {
	private final Interpretation interp;

	public Eval(Interpretation interp) {
		this.interp = interp;
	}

	@Override
	public Boolean visit(BinaryExpr e) {
		Boolean left = e.left.accept(this);
		Boolean right = e.right.accept(this);

		switch (e.op) {
		case AND:
			return left && right;
		case OR:
			return left || right;
		case XOR:
			return left != right;
		case EQUAL:
			return left == right;
		case IMPLIES:
			return !left || right;
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
		return !e.expr.accept(this);
	}

	@Override
	public Boolean visit(Var e) {
		return interp.get(e);
	}
}
