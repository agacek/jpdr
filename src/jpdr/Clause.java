package jpdr;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;

public class Clause {
	public final List<Literal> literals;

	public Clause(List<Literal> literals) {
		super();
		this.literals = Collections.unmodifiableList(literals);
	}
	
	public Cube negate() {
		return new Cube(literals.stream().map(Literal::negate).collect(toList()));
	}
	
	@Override
	public String toString() {
		return literals.stream().map(Literal::toString).collect(joining(" \\/ "));
	}
}
