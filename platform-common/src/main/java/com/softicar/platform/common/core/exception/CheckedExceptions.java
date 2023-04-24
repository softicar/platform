package com.softicar.platform.common.core.exception;

import com.softicar.platform.common.core.utils.CastUtils;

/**
 * Auxiliary methods for checked {@link Exception} handling.
 *
 * @author Oliver Richers
 */
public class CheckedExceptions {

	/**
	 * Converts the given {@link Throwable} into a {@link RuntimeException}.
	 * <p>
	 * The conversion is done by casting if possible. Otherwise the
	 * {@link Throwable} will be wrapped by a new {@link RuntimeException}.
	 *
	 * @param throwable
	 *            the {@link Throwable} to convert (never <i>null</i>)
	 * @return the converted {@link RuntimeException}
	 */
	public static RuntimeException toRuntimeException(Throwable throwable) {

		return CastUtils//
			.tryCast(throwable, RuntimeException.class)
			.orElseGet(() -> new RuntimeException(throwable));
	}

	public static void wrap(IThrowingNullaryVoidFunction function) {

		try {
			function.apply();
		} catch (RuntimeException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static <T> T wrap(IThrowingSupplier<T> supplier) {

		try {
			return supplier.get();
		} catch (RuntimeException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static interface IThrowingNullaryVoidFunction {

		void apply() throws Exception;
	}

	public static interface IThrowingSupplier<T> {

		T get() throws Exception;
	}
}
