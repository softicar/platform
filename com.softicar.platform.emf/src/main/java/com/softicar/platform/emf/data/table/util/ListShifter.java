package com.softicar.platform.emf.data.table.util;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListShifter {

	/**
	 * Manipulates the given list in-place by moving the element found at
	 * sourceIndex to targetIndex while not affecting the order of surrounding
	 * or intermediate elements. Think moving an item in a stack of elements.
	 *
	 * @param list
	 * @param sourceIndex
	 * @param targetIndex
	 */
	public static void shiftList(List<?> list, int sourceIndex, int targetIndex) {

		Objects.requireNonNull(list);
		assertInRange(list, sourceIndex);
		assertInRange(list, targetIndex);

		boolean sourceIndexGreater = sourceIndex > targetIndex;
		int lowerIndex = Math.min(sourceIndex, targetIndex);
		int upperIndex = Math.max(sourceIndex, targetIndex);
		Collections.rotate(list.subList(lowerIndex, upperIndex + 1), sourceIndexGreater? 1 : -1);
	}

	private static void assertInRange(List<?> list, int index) {

		if (index < 0 || index >= list.size()) {
			throw new IllegalArgumentException();
		}
	}
}
