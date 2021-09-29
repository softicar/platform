package com.softicar.platform.db.sql.utils;

import com.softicar.platform.common.container.comparing.ContainerComparing;
import com.softicar.platform.common.core.utils.CompareUtils;
import java.util.Comparator;

/**
 * Some comparison utility methods.
 * <p>
 * TODO: use {@link CompareUtils} and {@link ContainerComparing}
 *
 * @author Oliver Richers
 */
public class SqlComparisons {

	public static <T> int compareNullFirst(T left, T right, Comparator<T> comparator) {

		if (left == right) {
			return 0;
		} else if (left == null) {
			return -1;
		} else if (right == null) {
			return 1;
		} else {
			return comparator.compare(left, right);
		}
	}

	/**
	 * Executes a null-save comparison of the given objects.
	 *
	 * @param left
	 *            the object on the left hand
	 * @param right
	 *            the object on the right hand
	 * @return the comparison value
	 */
	public static <T extends Comparable<? super T>> int compare(T left, T right) {

		// TODO: removed this work-around after bug in JDK-8.25
//		return compareNullFirst(left, right, T::compareTo);
		return compareNullFirst(left, right, (a, b) -> a.compareTo(b));
	}

	public static <T extends Comparable<? super T>> int compare(Iterable<T> left, Iterable<T> right) {

		return compareNullFirst(left, right, ContainerComparing::compare);
	}

	public static int compare(byte[] left, byte[] right) {

		return compareNullFirst(left, right, ContainerComparing::compare);
	}
}
