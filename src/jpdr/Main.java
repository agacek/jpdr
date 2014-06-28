package jpdr;

import java.util.Arrays;

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
	}
}
