package jpdr.sat;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jpdr.eval.Interpretation;
import jpdr.expr.BinaryExpr;
import jpdr.expr.BoolExpr;
import jpdr.expr.Expr;
import jpdr.expr.ExprVisitor;
import jpdr.expr.NotExpr;
import jpdr.expr.Var;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.GateTranslator;

public class FastSat implements ExprVisitor<Integer> {
	public static Optional<Interpretation> check(Expr e) {
		return new FastSat(e).check();
	}

	private final Expr expr;
	private final ISolver solver = SolverFactory.instance().defaultSolver();
	private final GateTranslator translator = new GateTranslator(solver);
	private final BiMap<Var, Integer> map = new BiMap<>();
	private int freeVar = 1;

	public FastSat(Expr expr) {
		this.expr = expr;
		for (Var var : expr.getVars()) {
			map.put(var, freeVar++);
		}
	}

	private Optional<Interpretation> check() {
		try {
			// Translate to gates
			solver.addClause(new VecInt(new int[] { expr.accept(this) }));
			
			if (solver.isSatisfiable()) {
				Map<Var, Boolean> interp = new HashMap<>();
				BiMap<Integer, Var> inv = map.inverse();

				for (int i : solver.model()) {
					if (i > 0 && inv.containsKey(i)) {
						interp.put(inv.get(i), true);
					} else if (i < 0 && inv.containsKey(-i)) {
						interp.put(inv.get(-i), false);
					}
				}
				return Optional.of(new Interpretation(interp));
			} else {
				return Optional.empty();
			}
		} catch (TimeoutException | ContradictionException ex) {
			ex.printStackTrace();
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public Integer visit(BinaryExpr e) {
		try {
			int v = freeVar++;
			VecInt vec = new VecInt(new int[] { e.left.accept(this), e.right.accept(this) });

			switch (e.op) {
			case AND:
				translator.and(v, vec);
				break;

			case OR:
				translator.or(v, vec);
				break;

			case XOR:
				translator.xor(v, vec);
				break;

			case EQUAL:
				translator.iff(v, vec);
				break;

			case IMPLIES:
				translator.or(v, new VecInt(
						new int[] { -e.left.accept(this), e.right.accept(this) }));
				break;

			default:
				throw new IllegalArgumentException();
			}
			return v;
		} catch (ContradictionException ex) {
			ex.printStackTrace();
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public Integer visit(BoolExpr e) {
		try {
			int v = freeVar++;
			if (e.bool) {
				translator.gateTrue(v);
			} else {
				translator.gateFalse(v);
			}
			return v;
		} catch (ContradictionException ex) {
			ex.printStackTrace();
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public Integer visit(NotExpr e) {
		return -e.expr.accept(this);
	}

	@Override
	public Integer visit(Var e) {
		return map.get(e);
	}
}
