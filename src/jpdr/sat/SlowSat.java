package jpdr.sat;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jpdr.eval.Interpretation;
import jpdr.syntax.CNF;

public class SlowSat {
	public static Optional<Interpretation> check(CNF cnf) {
		return getInterpretations(cnf.getVariables()).filter(i -> i.eval(cnf)).findFirst();
	}

	private static Stream<Interpretation> getInterpretations(Set<String> vars) {
		Stream<Interpretation> stream = Stream.of(Interpretation.EMPTY);
		for (String var : vars) {
			stream = stream.flatMap(i -> Stream.of(i.add(var, true), i.add(var, false)));
		}
		return stream;
	}
}
