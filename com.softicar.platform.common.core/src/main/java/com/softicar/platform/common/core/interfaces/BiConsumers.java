package com.softicar.platform.common.core.interfaces;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Utility methods for the {@link BiConsumer} interface.
 *
 * @author Oliver Richers
 */
public class BiConsumers {

	private static final BiConsumer<Object, Object> NO_OPERATION = (x, y) -> {
		// nothing to do by design
	};

	@SuppressWarnings("unchecked")
	public static <T, S> BiConsumer<T, S> noOperation() {

		return (BiConsumer<T, S>) NO_OPERATION;
	}

	/**
	 * Binds the given argument as the first parameter of the given
	 * {@link BiConsumer}.
	 *
	 * @param consumer
	 *            the {@link BiConsumer} (never <i>null</i>)
	 * @param first
	 *            the argument for the first parameter (may be <i>null</i>)
	 * @return the new {@link Consumer} (never <i>null</i>)
	 */
	public static <T, S> Consumer<S> bindFirst(BiConsumer<T, S> consumer, T first) {

		Objects.requireNonNull(consumer);
		return second -> consumer.accept(first, second);
	}

	/**
	 * Binds the given argument as the second parameter of the given
	 * {@link BiConsumer}.
	 *
	 * @param consumer
	 *            the {@link BiConsumer} (never <i>null</i>)
	 * @param second
	 *            the argument for the first parameter (may be <i>null</i>)
	 * @return the new {@link Consumer} (never <i>null</i>)
	 */
	public static <T, S> Consumer<T> bindSecond(BiConsumer<T, S> consumer, S second) {

		Objects.requireNonNull(consumer);
		return first -> consumer.accept(first, second);
	}
}
