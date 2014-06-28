package jpdr.pdr;

import jpdr.syntax.CNF;

public class PDR {
	private final CNF I;
	private final CNF T;
	private final CNF P;

	public PDR(CNF I, CNF T, CNF P) {
		this.I = I;
		this.T = T;
		this.P = P;
	}
}
