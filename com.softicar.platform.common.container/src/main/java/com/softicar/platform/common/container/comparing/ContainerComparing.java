package com.softicar.platform.common.container.comparing;

import com.softicar.platform.common.core.utils.CompareUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

/**
 * This class contains comparing methods for various containers.
 *
 * @author Oliver Richers
 */
public class ContainerComparing {

	public static <A extends Comparable<B>, B> int compare(A[] a, B[] b) {

		return compare(Arrays.asList(a), Arrays.asList(b));
	}

	public static int compareNullFirst(byte[] left, byte[] right) {

		return CompareUtils.compareNullFirst(left, right, ContainerComparing::compare);
	}

	public static int compare(byte[] a, byte[] b) {

		int na = a.length;
		int nb = b.length;
		int n = Math.min(na, nb);
		for (int i = 0; i < n; ++i) {
			int cmp = a[i] - b[i];
			if (cmp != 0) {
				return cmp;
			}
		}
		return na - nb;
	}

	public static <T extends Comparable<? super T>> int compareNullFirst(Iterable<T> a, Iterable<T> b) {

		return CompareUtils.compareNullFirst(a, b, ContainerComparing::compare);
	}

	/**
	 * Compares two sequences lexicographically.
	 *
	 * @param <A>
	 *            element type of the first sequence
	 * @param <B>
	 *            element type of the second sequence
	 * @param a
	 *            first sequence
	 * @param b
	 *            second sequence
	 * @return an integer specifying the lexicographical ordering of the
	 *         sequences to each other
	 */
	public static <A extends Comparable<? super B>, B> int compare(Iterable<A> a, Iterable<B> b) {

		Iterator<A> itA = a.iterator();
		Iterator<B> itB = b.iterator();
		while (itA.hasNext()) {
			if (!itB.hasNext()) {
				return 1; // a is greater
			}

			// compare elements
			int cmp = itA.next().compareTo(itB.next());
			if (cmp != 0) {
				return cmp;
			}
		}

		return itB.hasNext()? -1 : 0;    // a is less or equal to b
	}

	public static <K, V> int compare(SortedMap<K, V> map1, SortedMap<K, V> map2) {

		Iterator<Map.Entry<K, V>> it1 = map1.entrySet().iterator();
		Iterator<Map.Entry<K, V>> it2 = map2.entrySet().iterator();
		while (true) {
			if (it1.hasNext()) {
				if (it2.hasNext()) {
					// get entries
					Map.Entry<K, V> entry1 = it1.next();
					Map.Entry<K, V> entry2 = it2.next();

					// compare keys
					int cmpK = CompareUtils.compareTo(entry1.getKey(), entry2.getKey());
					if (cmpK != 0) {
						return cmpK;
					}

					// compare values
					int cmpV = CompareUtils.compareTo(entry1.getValue(), entry2.getValue());
					if (cmpV != 0) {
						return cmpV;
					}
				} else {
					return 1; // it1 is shorter --> less
				}
			} else if (it2.hasNext()) {
				return 1; // it2 is longer
			} else {
				return 0; // it2 is equal
			}
		}
	}
}
