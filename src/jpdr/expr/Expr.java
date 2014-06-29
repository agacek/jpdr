package jpdr.expr;

import java.util.Set;

public interface Expr {
	public Expr prime(int n);
	public Set<Var> getVars();
	public <T> T accept(ExprVisitor<T> visitor);
	
	public static Var var(String name) {
		return new Var(name);
	}
	
	public static Expr and(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.AND, right);
	}
	
	public static Expr or(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.OR, right);
	}
	
	public static Expr xor(Expr left, Expr right) {
		return new BinaryExpr(left, BinaryOp.XOR, right);
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
}
