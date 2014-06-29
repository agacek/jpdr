package jpdr;

import static jpdr.expr.Expr.and;
import static jpdr.expr.Expr.equal;
import static jpdr.expr.Expr.not;
import static jpdr.expr.Expr.or;
import static jpdr.expr.Expr.xor;
import jpdr.bmc.BMC;
import jpdr.expr.Expr;
import jpdr.expr.Var;

public class Main {
	public static void main(String[] args) {
		Var a = new Var("a");
		Var b = new Var("b");
		Var c = new Var("c");
		
		Expr I = not(or(a, b, c));
				
		Expr Ta = equal(a.prime(), xor(a, and(b, c)));
		Expr Tb = equal(b.prime(), xor(b, c));
		Expr Tc = equal(c.prime(), not(c));
		Expr T = and(Ta, Tb, Tc);
		
		Expr P = not(and(a, c));
		
		System.out.println(new BMC(I, T, P, 10).check());
	}
}
