package com.softicar.platform.common.core.throwable;

import java.util.function.Predicate;

/**
 * Checks if a {@link Throwable} is caused by given {@link Throwable} class.
 *
 * @author Oliver Richers
 */
public class ThrowableCauseTester implements Predicate<Throwable> {

	private final Class<? extends Throwable> throwableClass;

	public ThrowableCauseTester(Class<? extends Throwable> throwableClass) {

		this.throwableClass = throwableClass;
	}

	@Override
	public boolean test(Throwable throwable) {

		if (throwableClass.isInstance(throwable)) {
			return true;
		} else {
			Throwable cause = throwable.getCause();
			if (cause != null) {
				return test(cause);
			} else {
				return false;
			}
		}
	}
}
