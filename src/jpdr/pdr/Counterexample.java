package jpdr.pdr;

import java.util.List;

import jpdr.eval.Interpretation;

public class Counterexample extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public final List<Interpretation> cex;

	public Counterexample(List<Interpretation> cex) {
		this.cex = cex;
	}
}
