package jpdr.sat;

import java.util.Optional;

import jpdr.eval.Interpretation;
import jpdr.expr.Expr;

public class Sat {
	public static Optional<Interpretation> check(Expr e) {
		return FastSat.check(e);
	}
}
