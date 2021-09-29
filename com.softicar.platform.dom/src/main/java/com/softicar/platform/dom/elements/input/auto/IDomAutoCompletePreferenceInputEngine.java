package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Collection;
import java.util.Optional;

public interface IDomAutoCompletePreferenceInputEngine<T> extends IDomAutoCompleteInputEngine<T> {

	/**
	 * Determines a Collection of items which match the pattern. Uses offset and
	 * size limitation.
	 * <p>
	 * <b>Implementations must comply to the following rules:</b><br>
	 * <b>1) Guaranteed determinism:</b> The returned items must be ordered in a
	 * guaranteed-deterministic way. That is: consecutive calls of this method
	 * must be guaranteed to yield the same items in the same order.<br>
	 * <b>2) Limitation must be applied:</b> fetchOffset and fetchSize must be
	 * used to extract a subsequence of items from all available items.<br>
	 * <b>3) Limitation must be the last operation:</b> Any filtering of the
	 * matching items must occur <i>before</i> the limitation (through
	 * fetchOffset and fetchSize; see above) is applied. To nevertheless perform
	 * filtering after applying fetchOffset and fetchSize, implement
	 * {@link #filterMatchingItems(String, Collection)}.
	 * <p>
	 * If at least one of those rules is violated, items offered for selection
	 * may be incomplete and/or redundant.
	 *
	 * @param pattern
	 *            the pattern (never null; may be the empty string; trimmed;
	 *            lower-case)
	 * @param fetchOffset
	 *            the offset to use while fetching for items (>= 0)
	 * @param fetchSize
	 *            the maximum number of items to fetch (>= 1)
	 * @return a Collection of items that match the given pattern (never null)
	 */
	Collection<T> findMatchingItems(String pattern, int fetchOffset, int fetchSize);

	/**
	 * Tries to find an item that is considered a perfect match for the given
	 * pattern.
	 * <p>
	 * Returns an empty Optional by default. Override if required.
	 *
	 * @param pattern
	 *            the pattern (never null; may be the empty string; trimmed;
	 *            lower-case)
	 * @return an item that is considered a perfect match for the given pattern,
	 *         or an empty Optional if no such item is identified (never null)
	 */
	default Optional<T> findPerfectMatch(String pattern) {

		DevNull.swallow(pattern);
		return Optional.empty();
	}

	/**
	 * Filters matching items in arbitrary ways. Applied to results of
	 * {@link #findMatchingItems(String, int, int)}.
	 *
	 * @param pattern
	 *            the pattern (never null; may be the empty string; trimmed;
	 *            lower-case)
	 * @param matchingItems
	 *            the matching items to be filtered (never null)
	 * @return a Collection of items which passed the filter
	 */
	default Collection<T> filterMatchingItems(String pattern, Collection<T> matchingItems) {

		DevNull.swallow(pattern);
		return matchingItems;
	}
}
