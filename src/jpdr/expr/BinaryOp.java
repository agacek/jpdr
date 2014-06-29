package jpdr.expr;

public enum BinaryOp {
	AND ("and"),
	OR ("or"),
	XOR ("xor"),
	EQUAL ("="),
	IMPLIES ("=>");

	private String str;
	
	private BinaryOp(String str) {
		this.str = str;
	}
	
	@Override
	public String toString() {
		return str;
	}
}
