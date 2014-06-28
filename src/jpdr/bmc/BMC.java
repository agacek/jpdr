package jpdr.bmc;

import java.util.Optional;

import jpdr.eval.Interpretation;
import jpdr.modelcheck.ModelChecker;
import jpdr.syntax.CNF;

public class BMC extends ModelChecker {
	private final int depth;

	public BMC(CNF I, CNF T, CNF P, int depth) {
		super(I, T, P);
		this.depth = depth;
	}

	@Override
	public Optional<Interpretation> check() {
		CNF query = CNF.EMPTY;
		for (int i = 0; i < depth; i++) {
			
		}
	}
}
