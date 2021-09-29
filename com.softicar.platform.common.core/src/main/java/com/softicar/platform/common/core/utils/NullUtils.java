package com.softicar.platform.common.core.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NullUtils {

	/**
	 * Applies the given {@link Function} to the given value iff the given value
	 * is not null.
	 * <p>
	 * Workaround for Java lacking a null coalescing operator.
	 *
	 * @param value
	 * @param applyMeIfNonNull
	 * @return Iff the given value is not null, the result of applying the given
	 *         {@link Function} to the given value is returned. Null otherwise.
	 */
	public static <V, R> R ifNotNull(V value, Function<V, R> applyMeIfNonNull) {

		return ifNotNull(value, applyMeIfNonNull, null);
	}

	/**
	 * WARNING: This method must not be used with a non-null third argument on a
	 * Java 1.8.0_45 runtime, due to a compiler bug.
	 */
	private static <V, R> R ifNotNull(V value, Function<V, R> applyMeIfNonNull, R returnMeIfNull) {

		return (value != null)? applyMeIfNonNull.apply(value) : returnMeIfNull;
	}

	public static <V> void consumeIfNotNull(V value, Consumer<V> applyMeIfNonNull) {

		if (value != null) {
			applyMeIfNonNull.accept(value);
		}
	}

	public static <V, R> R supplyIfNotNull(V value, Supplier<R> applyMeIfNonNull) {

		return (value != null)? applyMeIfNonNull.get() : null;
	}
}
