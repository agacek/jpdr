package jpdr.pdr;

import java.util.Optional;

import jpdr.eval.Interpretation;
import jpdr.expr.Expr;
import jpdr.modelcheck.ModelChecker;

public class PDR extends ModelChecker {
	public PDR(Expr I, Expr T, Expr P) {
		super(I, T, P);
	}

	@Override
	public Optional<Interpretation> check() {
		return null;
	}
}
