package jpdr.sat;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jpdr.eval.Interpretation;
import jpdr.expr.Expr;
import jpdr.expr.Var;

public class SlowSat {
	public static Optional<Interpretation> check(Expr e) {
		return getInterpretations(e.getVars()).filter(i -> i.eval(e)).findFirst();
	}

	private static Stream<Interpretation> getInterpretations(Set<Var> vars) {
		Stream<Interpretation> stream = Stream.of(Interpretation.EMPTY);
		for (Var var : vars) {
			stream = stream.flatMap(i -> Stream.of(i.add(var, true), i.add(var, false)));
		}
		return stream;
	}
}
