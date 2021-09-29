package com.softicar.platform.common.core.interfaces;

/**
 * A simple interface for every thing that can be refreshed.
 *
 * @author Boris Schaa
 * @author Oliver Richers
 */
public interface IRefreshable {

	/**
	 * A default implementation that does nothing.
	 */
	IRefreshable NO_OPERATION = () -> {
		// nothing to do by definition
	};

	void refresh();

	/**
	 * Checks the given null-able {@link IRefreshable} and returns a non-null
	 * {@link IRefreshable}.
	 * <p>
	 * If the given {@link IRefreshable} instance is <i>null</i> this returns
	 * {@link #NO_OPERATION}. Otherwise, the given {@link IRefreshable} instance
	 * is returned as it is.
	 *
	 * @param refreshable
	 *            the {@link IRefreshable} instance (may be null)
	 * @return the given {@link IRefreshable} instance or {@link #NO_OPERATION}
	 */
	static IRefreshable ofNullable(IRefreshable refreshable) {

		return refreshable == null? NO_OPERATION : refreshable;
	}
}
