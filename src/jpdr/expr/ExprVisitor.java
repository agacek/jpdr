package jpdr.expr;

public interface ExprVisitor<T> {
	public T visit(BinaryExpr e);
	public T visit(BoolExpr e);
	public T visit(NotExpr e);
	public T visit(Var e);
}
