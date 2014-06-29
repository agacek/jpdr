package jpdr.bmc;

import static java.util.stream.IntStream.range;
import static jpdr.expr.Expr.and;
import static jpdr.expr.Expr.not;

import java.util.ArrayList;
import java.util.List;
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
	public List<Interpretation> check() {
		List<Interpretation> result = new ArrayList<>();
		
		for (int k = 0; k < depth; k++) {
			Expr transitions = and(range(0, k).mapToObj(j -> T.prime(j)));
			Expr query = and(I, transitions, not(P.prime(k)));
			Optional<Interpretation> model = SlowSat.check(query);
			if (model.isPresent()) {
				for (int i = 0; i <= k; i++) {
					result.add(model.get().atStep(i));
				}
				return result;
			}
		}
		
		return result;
	}
}
