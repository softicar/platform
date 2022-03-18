package com.softicar.platform.common.core.exception;

/**
 * Auxiliary methods for checked {@link Exception} handling.
 *
 * @author Oliver Richers
 */
public class CheckedExceptions {

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
