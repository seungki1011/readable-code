package cleancode.studycafe.tobe.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Passes<T> {

	private final List<T> passes;

	private Passes(List<T> passes) {
		this.passes = passes;
	}

	public static <T> Passes<T> of(List<T> passes) {
		return new Passes<>(passes);
	}

	public Optional<StudyCafeLockerPass> filterLockerPassesBy(Predicate<T> condition) {
		return passes.stream()
			.filter(condition)
			.findFirst()
			.map(p -> (StudyCafeLockerPass) p);
	}

	public List<T> filterStudyCafePassesBy(Predicate<T> condition) {
		return passes.stream()
			.filter(condition)
			.toList();
	}

}
