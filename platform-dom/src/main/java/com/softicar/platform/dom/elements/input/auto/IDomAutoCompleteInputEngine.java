package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Collection;
import java.util.Collections;

public interface IDomAutoCompleteInputEngine<T> {

	/**
	 * Returns the {@link IDisplayString} of the given item (without
	 * description).
	 *
	 * @param item
	 *            the item to get the {@link IDisplayString} for
	 * @return the {@link IDisplayString} to use (never null)
	 */
	IDisplayString getDisplayString(T item);

	/**
	 * Determines a Collection that contains a limited number of items which
	 * match the pattern.
	 *
	 * @param pattern
	 *            the pattern (never null; may be the empty string; trimmed;
	 *            lower-case)
	 * @param limit
	 *            the maximum number of items to return (>= 1)
	 * @return a Collection of items that match the given pattern (never null)
	 */
	Collection<T> findMatches(String pattern, int limit);

	/**
	 * Returns a collection of active item filters.
	 * <p>
	 * The returned collection of filters will be displayed to the user.
	 *
	 * @return a collection of active filters (never null)
	 */
	default Collection<IDomAutoCompleteInputFilter> getFilters() {

		return Collections.emptyList();
	}

	/**
	 * This method is called to reload the internal state of the engine.
	 */
	default void refresh() {

		// nothing to do by default
	}

	/**
	 * This method is called to reload possibly cached values.
	 * <p>
	 * See PLAT-1055 for more details.
	 */
	default void reloadCache() {

		// nothing to do by default
	}
}
