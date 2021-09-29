package com.softicar.platform.common.core.throwable;

/**
 * Utility methods for exceptions.
 *
 * @author Oliver Richers
 */
public class Throwables {

	public static boolean isCausedBy(Class<? extends Throwable> throwableClass, Throwable throwable) {

		return new ThrowableCauseTester(throwableClass).test(throwable);
	}
}
