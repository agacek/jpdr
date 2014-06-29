package jpdr;

import static jpdr.expr.Expr.and;
import static jpdr.expr.Expr.equal;
import static jpdr.expr.Expr.xor;
import jpdr.expr.Expr;
import jpdr.expr.Var;
import jpdr.sat.SlowSat;

public class Main {
	public static void main(String[] args) {
		Var a = new Var("a");
		Var b = new Var("b");
		Var c = new Var("c");
		Var d = new Var("d");

		Expr e = equal(d, xor(c, and(a, b)));
		System.out.println(e);
		System.out.println(e.getVars());
		System.out.println(SlowSat.check(e));
	}
}
