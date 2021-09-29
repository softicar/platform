package com.softicar.platform.common.core.item;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility methods for basic items.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class BasicItemUtils {

	/**
	 * Returns the ID of the given item or null.
	 *
	 * @param item
	 *            the basic item to use
	 * @return the ID of the basic item, or null if the given item is null
	 */
	public static Integer getIdOrNull(IBasicItem item) {

		return item != null? item.getId() : null;
	}

	public static <T extends IBasicItem> Set<Integer> getIdSetFromBasicItems(Collection<T> items) {

		Map<Integer, T> result = getIdMapFromBasicItems(items);
		return result != null? result.keySet() : null;
	}

	public static <T extends IBasicItem> Map<Integer, T> getIdMapFromBasicItems(Collection<T> items) {

		if (items != null) {
			return items//
				.stream()
				.filter(item -> item != null && item.getId() != null)
				.collect(Collectors.toMap(IBasicItem::getId, item -> item));
		} else {
			return null;
		}
	}

	public static <T extends IBasicItem> Set<Long> getLongIdSetFromBasicItems(Collection<T> items) {

		Map<Long, T> result = getLongIdMapFromBasicItems(items);
		return result != null? result.keySet() : null;
	}

	public static <T extends IBasicItem> Map<Long, T> getLongIdMapFromBasicItems(Collection<T> items) {

		if (items != null) {
			return items//
				.stream()
				.filter(item -> item != null && item.getId() != null)
				.collect(Collectors.toMap(item -> item.getId().longValue(), item -> item));
		} else {
			return null;
		}
	}
}
