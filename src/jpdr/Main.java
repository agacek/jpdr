package jpdr;

import java.util.Arrays;

import syntax.CNF;
import syntax.Clause;
import syntax.Literal;


public class Main {
	public static void main(String[] args) {
		Literal a = new Literal("a");
		Literal b = new Literal("b", false);
		Literal c = new Literal("c");
		
		Clause k = new Clause(Arrays.asList(a, b, c));
		System.out.println(k);
		System.out.println(k.negate());
		System.out.println(new CNF(Arrays.asList(k, k)));
	}
}
