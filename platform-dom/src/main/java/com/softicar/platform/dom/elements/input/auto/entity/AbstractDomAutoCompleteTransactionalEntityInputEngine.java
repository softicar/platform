package com.softicar.platform.dom.elements.input.auto.entity;

import com.softicar.platform.common.core.entity.EntityIdFromDisplayStringExtractor;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.core.transaction.ITransaction;
import com.softicar.platform.dom.elements.input.auto.AbstractDomAutoCompletePreferenceInputEngine;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompletePreferenceInputEngine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractDomAutoCompleteTransactionalEntityInputEngine<T extends IEntity> extends AbstractDomAutoCompletePreferenceInputEngine<T> {

	protected abstract ITransaction createTransaction();

	@Override
	public IDisplayString getDisplayString(T item) {

		return item.toDisplay();
	}

	public IDisplayString getDisplayStringWithoutId(T item) {

		return item.toDisplayWithoutId();
	}

	/**
	 * A standard implementation that prefers a perfect match over regular
	 * matches. Performs de-duplication based on {@link IEntity} identities.
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

		// use a transaction to ensure repeatable reads
		try (ITransaction transaction = createTransaction()) {
			Collection<T> matches = super.findMatches(pattern, limit);
			Collection<T> deduplicatedMatches = getDeduplicated(matches);
			return deduplicatedMatches;
		}
	}

	/**
	 * A standard implementation that tries to find a perfect match by
	 * {@link IEntity} IDs.
	 *
	 * @param pattern
	 *            the pattern (never null; may be the empty string; trimmed;
	 *            lower-case)
	 * @return an item that is considered a perfect match for the given pattern,
	 *         or an empty Optional if no such item is identified (never null)
	 * @see IDomAutoCompletePreferenceInputEngine#findPerfectMatch(String)
	 */
	@Override
	public Optional<T> findPerfectMatch(String pattern) {

		if (pattern != null) {
			Optional<Integer> id = extractId(pattern.trim());
			if (id.isPresent()) {
				T entity = getById(id.get());
				if (entity != null) {
					return Optional.of(entity);
				}
			} else {
				// TODO provide a generic implementation of text based perfect matches
			}
		}
		return Optional.empty();
	}

	/**
	 * Tries to extract an {@link IEntity} ID from the given pattern. Used by
	 * the standard implementation of {@link #findPerfectMatch(String)}.
	 *
	 * @param trimmedPattern
	 *            a trimmed pattern to extract the ID from (never null)
	 * @return An Optional that contains the extracted ID. Empty if no ID could
	 *         be extracted.
	 */
	protected Optional<Integer> extractId(String trimmedPattern) {

		Optional<Integer> id = IntegerParser.parse(trimmedPattern);
		if (id.isPresent()) {
			return id;
		} else {
			return new EntityIdFromDisplayStringExtractor(trimmedPattern).extractId();
		}
	}

	/**
	 * Loads an item by {@link IEntity} ID. Used by the standard implementation
	 * of {@link #findPerfectMatch(String)}.
	 * <p>
	 * Note: Implementations must consider scopes (e.g. tenant).
	 *
	 * @param id
	 *            the ID of the item to load
	 * @return the item that matches the given id, and that is guaranteed to be
	 *         in a valid scope (may be null)
	 */
	protected abstract T getById(Integer id);

	/**
	 * Derives a new Collection of items from the given Collection while
	 * stripping duplicates. The first instance of all duplicates of an item are
	 * retained, while further instances are omitted.
	 *
	 * @param items
	 * @return a new Collection of items that corresponds to the given
	 *         Collection, stripped off all duplicates (never null)
	 */
	private Collection<T> getDeduplicated(Collection<T> items) {

		List<T> result = new ArrayList<>();
		Set<T> contained = new TreeSet<>();
		for (T item: items) {
			if (item != null) {
				if (!contained.contains(item)) {
					result.add(item);
					contained.add(item);
				}
			}
		}
		return result;
	}
}
