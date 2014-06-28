package jpdr.syntax;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CNF {
	public final List<Clause> clauses;

	public CNF(List<Clause> clauses) {
		this.clauses = Collections.unmodifiableList(clauses);
	}

	public Set<String> getVariables() {
		return clauses.stream().flatMap(c -> c.getVariables().stream()).collect(toSet());
	}
	
	@Override
	public String toString() {
		return clauses.stream().map(Object::toString).map(c -> "(" + c + ")")
				.collect(joining(" /\\ "));
	}
}
