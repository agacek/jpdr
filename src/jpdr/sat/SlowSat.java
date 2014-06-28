package jpdr.sat;

import java.util.HashSet;
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
		if (vars.isEmpty()) {
			return Stream.of(Interpretation.EMPTY);
		}

		String first = vars.iterator().next();
		Set<String> rest = new HashSet<>(vars);
		rest.remove(first);

		return getInterpretations(rest).flatMap(
				i -> Stream.of(i.add(first, true), i.add(first, false)));
	}
}
