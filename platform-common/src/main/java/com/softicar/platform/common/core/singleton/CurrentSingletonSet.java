package com.softicar.platform.common.core.singleton;

import java.util.Objects;

/**
 * Holds the currently active {@link SingletonSet} of the current
 * {@link Thread}.
 *
 * @author Oliver Richers
 */
public class CurrentSingletonSet {

	private static final SingletonSetThreadLocal INSTANCE = new SingletonSetThreadLocal();

	/**
	 * Returns the currently active {@link SingletonSet}.
	 *
	 * @return the current {@link SingletonSet} (never null)
	 */
	public static SingletonSet get() {

		return INSTANCE.get();
	}

	/**
	 * Defines the currently active {@link SingletonSet}.
	 * <p>
	 * This method should not be called directly. Instead,
	 * {@link SingletonSetScope} should be used.
	 *
	 * @param singletonSet
	 *            the {@link SingletonSet} to use (never null)
	 */
	public static void set(SingletonSet singletonSet) {

		Objects.requireNonNull(singletonSet);

		INSTANCE.set(singletonSet);
	}

	/**
	 * Removes the current {@link SingletonSet}.
	 */
	public static void remove() {

		INSTANCE.remove();
	}
}
