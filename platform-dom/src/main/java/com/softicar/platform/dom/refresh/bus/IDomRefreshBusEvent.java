package com.softicar.platform.dom.refresh.bus;

import java.util.Arrays;
import java.util.Collection;

/**
 * Represents an event object on the {@link IDomRefreshBus}.
 *
 * @author Oliver Richers
 */
public interface IDomRefreshBusEvent {

	/**
	 * Returns whether the given object is to be considered to be changed.
	 *
	 * @param object
	 *            the object to test (never null)
	 * @return true if the given object is to be considered changed
	 */
	boolean isChanged(Object object);

	/**
	 * Checks if any entity of the given classes is to be considered changed.
	 *
	 * @param classes
	 *            the classes to check
	 * @return true if any entity of the given classes is to be considered
	 *         changed
	 */
	default boolean isAnyObjectChanged(Class<?>...classes) {

		return isAnyObjectChanged(Arrays.asList(classes));
	}

	/**
	 * Checks if any entity of the given classes is to be considered changed.
	 *
	 * @param classes
	 *            the classes to check
	 * @return true if any entity of the given classes is to be considered
	 *         changed
	 */
	boolean isAnyObjectChanged(Collection<Class<?>> classes);

	/**
	 * Checks if the "all-changed" flag was set.
	 *
	 * @return true if the "all-changed" flag was set
	 */
	boolean isAllChanged();
}
