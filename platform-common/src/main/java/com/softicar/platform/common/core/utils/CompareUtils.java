package com.softicar.platform.common.core.utils;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.Comparator;

/**
 * Utility methods for comparison.
 *
 * @author Oliver Richers
 */
public class CompareUtils {

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

	public static <T extends Comparable<? super T>> int compareNullFirst(T left, T right) {

		return compareNullFirst(left, right, (a, b) -> a.compareTo(b));
	}

	/**
	 * Forces the comparison of two objects.
	 * <p>
	 * This method expects that the first object implements the
	 * {@link Comparable} interface and that the second object is an instance of
	 * the same class or derived class of the first object.
	 *
	 * @param a
	 *            first object
	 * @param b
	 *            second object
	 * @return the comparison result as defined by {@link Comparable}
	 * @throws SofticarDeveloperException
	 *             if the first element does not implement {@link Comparable} or
	 *             if the second object is not of the same or a derived class.
	 */
	public static int compareTo(Object a, Object b) {

		if (a instanceof Comparable<?> && a.getClass().isInstance(b)) {
			Comparable<Object> aa = CastUtils.cast(a);
			return aa.compareTo(b);
		} else {
			throw new SofticarDeveloperException("Tried to compare objects of type %s to object of type %s.", a.getClass(), b.getClass());
		}
	}

	/**
	 * This method checks for null-pointers before executing a.equals(b).
	 *
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 * @return true if a and b are null, the same or a.equals(b), false
	 *         otherwise
	 */
	public static boolean isEqual(Object a, Object b) {

		if (a == b) {
			return true;
		}

		if (a == null || b == null) {
			return false;
		}

		return a.equals(b);
	}
}
