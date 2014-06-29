package jpdr.expr;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

public interface Expr {
	public Expr prime(int n);

	public Set<Var> getVars();

	public <T> T accept(ExprVisitor<T> visitor);

	public static Var var(String name) {
		return new Var(name);
	}
	
	public static BoolExpr TRUE = new BoolExpr(true);
	public static BoolExpr FALSE = new BoolExpr(false);

	public static Expr and(Expr... e) {
		return and(Arrays.asList(e).stream());
	}
	
	public static Expr and(Stream<Expr> es) {
		return es.reduce((l, r) -> new BinaryExpr(l, BinaryOp.AND, r)).orElse(TRUE);
	}

	public static Expr or(Expr... e) {
		return or(Arrays.asList(e).stream());
	}
	
	public static Expr or(Stream<Expr> es) {
		return es.reduce((l, r) -> new BinaryExpr(l, BinaryOp.OR, r)).orElse(FALSE);
	}

	public static Expr xor(Expr... e) {
		return xor(Arrays.asList(e).stream());
	}
	
	public static Expr xor(Stream<Expr> es) {
		return es.reduce((l, r) -> new BinaryExpr(l, BinaryOp.XOR, r)).orElse(FALSE);
	}

	public static Expr equal(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.EQUAL, right);
	}

	public static Expr implies(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.IMPLIES, right);
	}

	public static Expr not(Expr expr) {
		return new NotExpr(expr);
	}
	
	public default Expr prime() {
		return prime(1);
	}
}
