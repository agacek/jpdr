package jpdr;

import static jpdr.expr.Expr.and;
import static jpdr.expr.Expr.equal;
import static jpdr.expr.Expr.not;
import static jpdr.expr.Expr.or;
import static jpdr.expr.Expr.xor;
import jpdr.eval.Interpretation;
import jpdr.expr.Expr;
import jpdr.expr.Var;
import jpdr.modelcheck.ModelChecker;
import jpdr.pdr.PDR;

public class Main {
	public static void main(String[] args) {
		Var a = new Var("a");
		Var b = new Var("b");
		Var c = new Var("c");
		Var a2 = new Var("a2");
		Var b2 = new Var("b2");
		Var c2 = new Var("c2");

		Expr I = not(or(a, b, c, a2, b2, c2));

		Expr Ta = equal(a.prime(), xor(a, and(b, c)));
		Expr Tb = equal(b.prime(), xor(b, c));
		Expr Tc = equal(c.prime(), not(c));
		Expr Ta2 = equal(a2.prime(), xor(a2, and(b2, c2)));
		Expr Tb2 = equal(b2.prime(), xor(b2, c2));
		Expr Tc2 = equal(c2.prime(), not(c2));
		Expr T = and(Ta, Tb, Tc, Ta2, Tb2, Tc2);

		Expr P = and(equal(a, a2), equal(b, b2), equal(c, c2));

		long start = System.currentTimeMillis();
		// ModelChecker mc = new BMC(I, T, P, 10);
		ModelChecker mc = new PDR(I, T, P);
		for (Interpretation interp : mc.check()) {
			System.out.println(interp);
		}
		long stop = System.currentTimeMillis();
		System.out.println("Time: " + (stop - start) / 1000.0);
		((PDR) mc).showFrames();
	}
}
