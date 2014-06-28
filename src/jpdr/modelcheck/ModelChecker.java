package jpdr.modelcheck;

import java.util.Optional;

import jpdr.eval.Interpretation;
import jpdr.syntax.CNF;

public abstract class ModelChecker {
	protected final CNF I;
	protected final CNF T;
	protected final CNF P;

	public ModelChecker(CNF I, CNF T, CNF P) {
		this.I = I;
		this.T = T;
		this.P = P;
	}
	
	public abstract Optional<Interpretation> check();
}
