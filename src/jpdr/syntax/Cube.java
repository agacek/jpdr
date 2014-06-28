package jpdr.syntax;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.Set;

public class Cube {
	public final Set<Literal> literals;

	public Cube(Set<Literal> literals) {
		this.literals = Collections.unmodifiableSet(literals);
	}
	
	public Clause negate() {
		return new Clause(literals.stream().map(Literal::negate).collect(toSet()));
	}

	public Cube prime() {
		return new Cube(literals.stream().map(Literal::prime).collect(toSet()));
	}
	
	public Set<String> getVariables() {
		return literals.stream().map(l -> l.var).collect(toSet());
	}
	
	@Override
	public String toString() {
		return literals.stream().map(Object::toString).collect(joining(" /\\ "));
	}
}
