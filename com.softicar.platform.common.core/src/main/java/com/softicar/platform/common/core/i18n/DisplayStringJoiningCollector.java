package com.softicar.platform.common.core.i18n;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Similar to {@link Collectors#joining}, but for {@link IDisplayString}.
 *
 * @author Oliver Richers
 */
public class DisplayStringJoiningCollector implements Collector<IDisplayString, Collection<IDisplayString>, IDisplayString> {

	private final IDisplayString separator;

	public DisplayStringJoiningCollector(IDisplayString separator) {

		this.separator = separator;
	}

	@Override
	public Supplier<Collection<IDisplayString>> supplier() {

		return ArrayList::new;
	}

	@Override
	public BiConsumer<Collection<IDisplayString>, IDisplayString> accumulator() {

		return Collection::add;
	}

	@Override
	public BinaryOperator<Collection<IDisplayString>> combiner() {

		return (a, b) -> {
			a.addAll(b);
			return a;
		};
	}

	@Override
	public Function<Collection<IDisplayString>, IDisplayString> finisher() {

		return displayStrings -> {
			DisplayString result = new DisplayString();
			for (IDisplayString string: displayStrings) {
				if (!result.isEmpty()) {
					result.append(separator);
				}
				result.append(string);
			}
			return result;
		};
	}

	@Override
	public Set<Characteristics> characteristics() {

		return Collections.emptySet();
	}
}
