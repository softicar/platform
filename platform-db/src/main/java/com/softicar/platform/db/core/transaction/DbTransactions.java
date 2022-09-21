package com.softicar.platform.db.core.transaction;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Provides various utility methods concerning {@link DbTransaction}.
 *
 * @author Oliver Richers
 */
public class DbTransactions {

	/**
	 * Wraps the {@link Supplier#get} call in a {@link DbTransaction}.
	 *
	 * @param <T>
	 *            the type of the {@link Supplier} result
	 * @param supplier
	 *            the {@link Supplier} (never <i>null</i>)
	 * @return the wrapped {@link Supplier} (never <i>null</i>)
	 */
	public static <T> Supplier<T> wrap(Supplier<T> supplier) {

		Objects.requireNonNull(supplier);

		return () -> {
			try (DbTransaction transaction = new DbTransaction()) {
				T result = supplier.get();
				transaction.commit();
				return result;
			}
		};
	}

	/**
	 * Wraps the {@link Consumer#accept} call in a {@link DbTransaction}.
	 *
	 * @param <T>
	 *            the type of the {@link Consumer} parameter
	 * @param consumer
	 *            the {@link Consumer} (never <i>null</i>)
	 * @return the wrapped {@link Consumer} (never <i>null</i>)
	 */
	public static <T> Consumer<T> wrap(Consumer<T> consumer) {

		Objects.requireNonNull(consumer);

		return parameter -> {
			try (DbTransaction transaction = new DbTransaction()) {
				consumer.accept(parameter);
				transaction.commit();
			}
		};
	}

	/**
	 * Wraps the {@link Function#apply} call in a {@link DbTransaction}.
	 *
	 * @param <T>
	 *            the type of the {@link Function} parameter
	 * @param <R>
	 *            the type of the {@link Function} result
	 * @param function
	 *            the {@link Function} (never <i>null</i>)
	 * @return the wrapped {@link Function} (never <i>null</i>)
	 */
	public static <T, R> Function<T, R> wrap(Function<T, R> function) {

		Objects.requireNonNull(function);

		return parameter -> {
			try (DbTransaction transaction = new DbTransaction()) {
				R result = function.apply(parameter);
				transaction.commit();
				return result;
			}
		};
	}
}
