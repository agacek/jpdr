package syntax;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CNF {
	public final List<Clause> clauses;

	public CNF(List<Clause> clauses) {
		super();
		this.clauses = Collections.unmodifiableList(clauses);
	}

	public DNF negate() {
		return new DNF(clauses.stream().map(Clause::negate).collect(Collectors.toList()));
	}

	@Override
	public String toString() {
		return clauses.stream().map(Object::toString).map(c -> "(" + c + ")")
				.collect(joining(" /\\ "));
	}
}
