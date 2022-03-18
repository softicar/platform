package com.softicar.platform.common.core.interfaces;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Utility methods for the {@link Consumer} interface.
 *
 * @author Oliver Richers
 */
public class Consumers {

	private static final Consumer<Object> NO_OPERATION = x -> {
		// nothing to do by design
	};

	@SuppressWarnings("unchecked")
	public static <T> Consumer<T> noOperation() {

		return (Consumer<T>) NO_OPERATION;
	}

	public static <T> boolean isNoOperation(Consumer<T> consumer) {

		return consumer == NO_OPERATION;
	}

	@SafeVarargs
	public static <T> Consumer<T> concat(Consumer<T>...consumers) {

		return concat(Arrays.asList(consumers));
	}

	public static <T> Consumer<T> concat(Collection<Consumer<T>> consumers) {

		return (T object) -> consumers.forEach(consumer -> consumer.accept(object));
	}

	public static <T, R> Function<T, R> toFunction(Consumer<T> consumer, R result) {

		return value -> {
			consumer.accept(value);
			return result;
		};
	}
}
