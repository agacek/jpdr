package jpdr.syntax;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Clause {
	public final Set<Literal> literals;

	public Clause(Set<Literal> literals) {
		this.literals = Collections.unmodifiableSet(literals);
	}

	public Clause(List<Literal> literals) {
		this(new HashSet<>(literals));
	}
	
	public Cube negate() {
		return new Cube(literals.stream().map(Literal::negate).collect(toSet()));
	}
	
	public Clause prime() {
		return new Clause(literals.stream().map(Literal::prime).collect(toSet()));
	}
	
	public boolean isSubclauseOf(Clause other) {
		return literals.stream().allMatch(other.literals::contains);
	}
	
	public Clause remove(Literal literal) {
		HashSet<Literal> set = new HashSet<>(literals);
		set.remove(literal);
		return new Clause(set);
	}
	
	public Set<String> getVariables() {
		return literals.stream().map(l -> l.var).collect(toSet());
	}
	
	@Override
	public String toString() {
		return literals.stream().map(Object::toString).collect(joining(" \\/ "));
	}
}
