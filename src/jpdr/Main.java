package jpdr;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.stream.IntStream;

import jpdr.sat.SlowSat;
import jpdr.syntax.CNF;
import jpdr.syntax.Clause;
import jpdr.syntax.Literal;

public class Main {
	public static void main(String[] args) {
		Literal a = new Literal("a", false);
		Literal b = new Literal("b", false);
		Literal c = new Literal("c", false);

		Clause k1 = new Clause(Arrays.asList(a, b, c));
		System.out.println(k1);
		System.out.println(k1.negate());

		Clause k2 = new Clause(Arrays.asList(new Literal("d", false)));
		CNF cnf = new CNF(Arrays.asList(k1, k2));
		System.out.println(cnf);
		System.out.println(cnf.getVariables());
		System.out.println(SlowSat.check(cnf));

		CNF cnf2 = new CNF(IntStream.range(0, 18)
				.mapToObj(i -> new Clause(Arrays.asList(new Literal("x" + i, false))))
				.collect(toList()));
		System.out.println(cnf2);
		System.out.println(SlowSat.check(cnf2));
	}
}
