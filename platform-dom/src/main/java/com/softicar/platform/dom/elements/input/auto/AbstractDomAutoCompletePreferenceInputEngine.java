package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public abstract class AbstractDomAutoCompletePreferenceInputEngine<T> implements IDomAutoCompletePreferenceInputEngine<T> {

	/**
	 * A standard implementation that prefers a perfect match over regular
	 * matches.
	 *
	 * @param pattern
	 *            the pattern (never null; may be the empty string; trimmed;
	 *            lower-case)
	 * @param limit
	 *            the maximum number of items to return (>= 1)
	 * @return a Collection of items that match the given pattern (never null)
	 * @see IDomAutoCompleteInputEngine#findMatches(String, int)
	 */
	@Override
	public Collection<T> findMatches(String pattern, int limit) {

		return findPerfectMatchSafe(pattern) //
			.map(it -> (Collection<T>) Collections.singleton(it))
			.orElse(new ItemFetcher(pattern, limit).fetch());
	}

	/**
	 * Suppresses Exceptions thrown by implementations of
	 * {@link #findPerfectMatch(String)}.
	 */
	private Optional<T> findPerfectMatchSafe(String pattern) {

		try {
			return findPerfectMatch(pattern);
		} catch (Exception exception) {
			logException(exception);
			return Optional.empty();
		}
	}

	/**
	 * Suppresses Exceptions thrown by implementations of
	 * {@link #findMatchingItems(String, int, int)}.
	 */
	private Collection<T> findMatchingItemsSafe(String pattern, int fetchOffset, int fetchSize) {

		try {
			return Optional//
				.ofNullable(findMatchingItems(pattern, fetchOffset, fetchSize))
				.orElse(Collections.emptyList());
		} catch (Exception exception) {
			logException(exception);
			return Collections.emptyList();
		}
	}

	/**
	 * Suppresses Exceptions thrown by implementations of
	 * {@link #filterMatchingItems(String, Collection)}.
	 */
	private Collection<T> filterMatchingItemsSafe(String pattern, Collection<T> matchingItems) {

		try {
			return filterMatchingItems(pattern, matchingItems);
		} catch (Exception exception) {
			logException(exception);
			return Collections.emptyList();
		}
	}

	private void logException(Exception exception) {

		// TODO chances are that nobody will ever see this. but a panic event would be too much. reconsider.
		Log.ferror(StackTraceFormatting.getStackTraceAsString(exception));
	}

	private class ItemFetcher {

		private final String pattern;
		private final int limit;
		private final Collection<T> items;

		public ItemFetcher(String pattern, int limit) {

			this.pattern = pattern;
			this.limit = limit;
			this.items = new ArrayList<>();
		}

		public Collection<T> fetch() {

			items.clear();
			// TODO this offset must be customizable to allow for paging in auto-complete popups
			int fetchOffset = 0;
			int fetchSize = limit;
			while (items.size() < limit) {
				boolean done = fetchSome(fetchOffset, fetchSize);
				if (done) {
					break;
				}
				fetchOffset += fetchSize;
				fetchSize *= 2;
			}
			return items;
		}

		private boolean fetchSome(int fetchOffset, int fetchSize) {

			Collection<T> matchingItems = findMatchingItemsSafe(pattern, fetchOffset, fetchSize);
			Collection<T> filtered = filterMatchingItemsSafe(pattern, matchingItems);
			for (T entity: filtered) {
				if (items.size() < limit) {
					items.add(entity);
				} else {
					// got enough items
					return true;
				}
			}
			// got all available items?
			return matchingItems.size() < fetchSize;
		}
	}
}
