package jpdr.pdr;

import java.util.Optional;

import jpdr.eval.Interpretation;
import jpdr.modelcheck.ModelChecker;
import jpdr.syntax.CNF;

public class PDR extends ModelChecker {
	public PDR(CNF I, CNF T, CNF P) {
		super(I, T, P);
	}

	@Override
	public Optional<Interpretation> check() {
		return null;
	}
}
