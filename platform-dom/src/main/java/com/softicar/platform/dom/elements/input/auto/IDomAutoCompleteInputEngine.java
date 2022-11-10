package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.input.auto.matching.IAutoCompleteMatches;
import java.util.Collection;
import java.util.Collections;

/**
 * Provides values available for selection in an {@link DomAutoCompleteInput}.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 * @author Oliver Richers
 */
public interface IDomAutoCompleteInputEngine<T> {

	/**
	 * Returns the {@link IDisplayString} of the given value.
	 *
	 * @param value
	 *            the value to get the {@link IDisplayString} for (never
	 *            <i>null</i>)
	 * @return the {@link IDisplayString} to use (never <i>null</i>)
	 */
	IDisplayString getDisplayString(T value);

	/**
	 * Determines an {@link IAutoCompleteMatches} instance that contains a
	 * limited number of values which match the pattern.
	 *
	 * @param pattern
	 *            the pattern (never <i>null</i>; may be empty; trimmed;
	 *            lower-case)
	 * @param limit
	 *            the maximum number of matches to find (at least 1)
	 * @return an {@link IAutoCompleteMatches} instance that contains the result
	 *         (never <i>null</i>)
	 */
	IAutoCompleteMatches<T> findMatches(String pattern, int limit);

	/**
	 * Returns a {@link Collection} of active value filters.
	 *
	 * @return the active filters (never <i>null</i>)
	 */
	default Collection<IDomAutoCompleteInputFilter> getFilters() {

		return Collections.emptyList();
	}

	/**
	 * Reloads the internal state of this {@link IDomAutoCompleteInputEngine}.
	 */
	default void refresh() {

		// nothing to do by default
	}

	/**
	 * Reloads possibly-cached values.
	 * <p>
	 * TODO PLAT-1055 this might require optimization
	 */
	default void reloadCache() {

		// nothing to do by default
	}
}
