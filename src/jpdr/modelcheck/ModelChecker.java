package jpdr.modelcheck;

import java.util.List;

import jpdr.eval.Interpretation;
import jpdr.expr.Expr;

public abstract class ModelChecker {
	protected final Expr I;
	protected final Expr T;
	protected final Expr P;

	public ModelChecker(Expr I, Expr T, Expr P) {
		this.I = I;
		this.T = T;
		this.P = P;
	}
	
	public abstract List<Interpretation> check();
}
