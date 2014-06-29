package jpdr;

import static jpdr.expr.Expr.and;
import static jpdr.expr.Expr.equal;
import static jpdr.expr.Expr.not;
import static jpdr.expr.Expr.or;
import static jpdr.expr.Expr.xor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import jpdr.bmc.BMC;
import jpdr.eval.Interpretation;
import jpdr.expr.Expr;
import jpdr.expr.Var;
import jpdr.modelcheck.ModelChecker;

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
		
		ModelChecker mc = new BMC(I, T, P, 10);
		for (Interpretation interp : separate(mc.check())) {
			System.out.println(interp);
		}
	}
	
	public static List<Interpretation> separate(Optional<Interpretation> optional) {
		if (!optional.isPresent()) {
			return Collections.emptyList();
		}
		Interpretation interp = optional.get();
		
		List<Interpretation> result = new ArrayList<>();
		for (int i = 0; true; i++) {
			Interpretation slice = interp.atStep(i);
			if (slice.isEmpty()) {
				return result;
			}
			result.add(slice);
		}
	}
}
