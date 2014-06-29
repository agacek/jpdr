package jpdr.bmc;

import static java.util.stream.IntStream.range;
import static jpdr.expr.Expr.*;

import java.util.Optional;

import jpdr.eval.Interpretation;
import jpdr.expr.Expr;
import jpdr.modelcheck.ModelChecker;
import jpdr.sat.SlowSat;

public class BMC extends ModelChecker {
	private final int depth;

	public BMC(Expr I, Expr T, Expr P, int depth) {
		super(I, T, P);
		this.depth = depth;
	}

	@Override
	public Optional<Interpretation> check() {
		for (int i = 0; i < depth; i++) {
			Expr transitions = and(range(0, i).mapToObj(j -> T.prime(j)));
			Expr query = and(I, transitions, not(P.prime(i)));
			Optional<Interpretation> model = SlowSat.check(query);
			if (model.isPresent()) {
				return model;
			}
		}
		return Optional.empty();
	}
}
