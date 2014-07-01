package jpdr;

import java.io.FileInputStream;
import java.io.InputStream;

import jpdr.aiger.JaigerToExpr;
import jpdr.eval.Interpretation;
import jpdr.expr.Expr;
import jpdr.pdr.PDR;

public class Main {
	public static void main(String[] args) throws Exception {
		try (InputStream in = new FileInputStream("c:/desktop/test.aig")) {
			JaigerToExpr reader = new JaigerToExpr(in);
			reader.parse();
			Expr I = reader.getInitial();
			Expr T = reader.getTransition();
			Expr P = reader.getProperty();
			check(I, T, P);
		}
	}

	private static void check(Expr I, Expr T, Expr P) {
		long start = System.currentTimeMillis();
		// BMC mc = new BMC(I, T, P, 30);
		PDR mc = new PDR(I, T, P);
		for (Interpretation interp : mc.check()) {
			System.out.println(interp);
		}
		long stop = System.currentTimeMillis();
		System.out.println("Time: " + (stop - start) / 1000.0);
		// mc.showFrames();
		System.out.println();
	}
}
