package syntax;

import static java.util.stream.Collectors.joining;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DNF {
	public final List<Cube> cubes;

	public DNF(List<Cube> cubes) {
		super();
		this.cubes = Collections.unmodifiableList(cubes);
	}
	
	public CNF negate() {
		return new CNF(cubes.stream().map(Cube::negate).collect(Collectors.toList()));
	}
	
	@Override
	public String toString() {
		return cubes.stream().map(Object::toString).map(c -> "(" + c + ")")
				.collect(joining(" \\/ "));
	}
}
