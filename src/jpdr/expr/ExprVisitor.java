package jpdr.expr;

public interface ExprVisitor<T> {
	public T visit(Var e);
	public T visit(BinaryExpr e);
	public T visit(NotExpr e);
}
