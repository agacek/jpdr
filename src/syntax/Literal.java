package syntax;

public class Literal {
	public final String var;
	public final boolean positive;

	public Literal(String var, boolean positive) {
		super();
		this.var = var;
		this.positive = positive;
	}
	
	public Literal(String var) {
		this(var, true);
	}
	
	public Literal negate() {
		return new Literal(var, !positive);
	}
	
	@Override
	public String toString() {
		return (positive ? "" : "~") + var;
	}

	@Override
	public int hashCode() {
		return 31 * var.hashCode() + Boolean.hashCode(positive);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Literal) {
			Literal other = (Literal) obj;
			return var.equals(other.var) && positive == other.positive;
		}
		return false;
	}
}
