package syntax;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Cube {
	public final List<Literal> literals;

	public Cube(List<Literal> literals) {
		super();
		this.literals = Collections.unmodifiableList(literals);
	}
	
	public Clause negate() {
		return new Clause(literals.stream().map(Literal::negate).collect(Collectors.toList()));
	}
	
	@Override
	public String toString() {
		return literals.stream().map(Object::toString).collect(joining(" /\\ "));
	}
}
