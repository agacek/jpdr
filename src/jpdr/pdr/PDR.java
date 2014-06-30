package jpdr.pdr;

import static java.util.stream.Collectors.toList;
import static jpdr.expr.Expr.and;
import static jpdr.expr.Expr.not;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jpdr.eval.Interpretation;
import jpdr.eval.TernaryEval;
import jpdr.expr.Expr;
import jpdr.expr.Var;
import jpdr.modelcheck.ModelChecker;
import jpdr.sat.SlowSat;

public class PDR extends ModelChecker {
	private final List<Frame> R = new ArrayList<>();
	private final Map<Cube, Cube> chains = new HashMap<>();
	private int N = 0;

	public PDR(Expr I, Expr T, Expr P) {
		super(I, T, P);
	}

	@Override
	public List<Interpretation> check() {
		R.add(new Frame(I));
		try {
			while (true) {
				Expr query = and(R.get(N).toExpr(), not(P));
				Optional<Interpretation> res = SlowSat.check(query);
				if (res.isPresent()) {
					Cube s = generalize(res.get().toCube(), query);
					block(s, N);
				} else {
					propogateClauses();
					if (existsEqualFrames()) {
						return Collections.emptyList();
					}
					R.add(new Frame());
					N++;
				}
			}
		} catch (Counterexample cex) {
			return cex.cex;
		}
	}

	private void block(Cube s, int k) {
		if (k == 0) {
			Optional<Interpretation> res = SlowSat.check(and(I, s.toExpr()));
			if (res.isPresent()) {
				List<Cube> cubes = new ArrayList<>();
				Cube curr = s;
				while (curr != null) {
					cubes.add(curr);
					curr = chains.get(curr);
				}
				throw new Counterexample(cubes.stream().map(Cube::toInterpretation)
						.collect(toList()));
			}
		} else {
			while (true) {
				Expr query = and(R.get(k - 1).toExpr(), s.negate().toExpr(), T, s.prime().toExpr());
				Optional<Interpretation> res = SlowSat.check(query);
				if (res.isPresent()) {
					// Cube is unblocked
					Cube t = generalize(res.get().atStep(0).toCube(), query);
					chains.put(t, s);
					block(t, k - 1);
				} else {
					break;
				}
			}

			// Cube is blocked at k and all previous steps
			for (int i = 1; i <= k; i++) {
				R.get(i).addClause(s.negate());
			}
		}
	}

	private void propogateClauses() {
		for (int k = 1; k < N; k++) {
			for (Clause c : R.get(k).getClauses()) {
				Expr query = and(R.get(k).toExpr(), T, c.prime().toExpr());
				Optional<Interpretation> res = SlowSat.check(query);
				if (!res.isPresent()) {
					R.get(k + 1).addClause(c);
				}
			}
		}
	}

	private boolean existsEqualFrames() {
		for (int k = 1; k < N; k++) {
			if (R.get(k).equals(R.get(k + 1))) {
				return true;
			}
		}
		return false;
	}

	private Cube generalize(Cube c, Expr query) {
		Interpretation interp = c.toInterpretation();
		for (Var v : c.getVars()) {
			Interpretation reduced = interp.remove(v);
			if (query.accept(new TernaryEval(reduced)) != null) {
				interp = reduced;
			}
		}
		Cube c2 = interp.toCube();
		if (!c.equals(c2)) {
			System.out.println("Gen: " + c + " to " + c2);
		}
		return c2;
	}

	public void showFrames() {
		for (int k = 1; k <= N; k++) {
			System.out.println("Frame " + k + ": " + R.get(k));
		}
	}
}
