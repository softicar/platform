package com.softicar.platform.common.container.set;

import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This class contains static factory methods to conveniently create instances
 * of various sets.
 * 
 * @author Oliver Richers
 */
public abstract class SetFactory {

	/**
	 * Creates a new and empty hash set.
	 */
	public static <V> HashSet<V> createHashSet() {

		return new HashSet<>();
	}

	/**
	 * Creates a new hash set and inserts the values of the specified
	 * collection.
	 */
	public static <V> HashSet<V> createHashSet(Collection<? extends V> collection) {

		return new HashSet<>(collection);
	}

	/**
	 * Creates a new hash set and inserts the specified values.
	 */
	@SafeVarargs
	public static <V> HashSet<V> createHashSetFrom(V...values) {

		return new HashSet<>(Arrays.asList(values));
	}

	/**
	 * Creates a new and empty tree set.
	 */
	public static <V> TreeSet<V> createTreeSet() {

		return new TreeSet<>();
	}

	public static <V> TreeSet<V> createTreeSet(Comparator<? super V> comparator) {

		return new TreeSet<>(comparator);
	}

	/**
	 * Creates a new tree set and inserts the values of the specified
	 * {@link Iterable}.
	 * <p>
	 * If the specified iterable is in fact a {@link SortedSet}, this redirects
	 * to the theoretically more efficient method
	 * {@link #createTreeSet(SortedSet)}, which will also transfer the
	 * comparator to the new set.
	 * <p>
	 * If the specified iterable is not a sorted set, the default comparator is
	 * used, and the copy operation runs in about O(n*log n).
	 */
	public static <V> TreeSet<V> createTreeSet(Iterable<? extends V> iterable) {

		// check if iterable is in fact a sorted set
		if (iterable instanceof SortedSet<?>) {
			SortedSet<V> sortedSet = CastUtils.cast(iterable);
			return createTreeSet(sortedSet);
		}

		// check if iterable is in fact a collection
		if (iterable instanceof Collection<?>) {
			Collection<V> collection = CastUtils.cast(iterable);
			return new TreeSet<>(collection);
		}

		// insert the elements one-by-one
		TreeSet<V> treeSet = createTreeSet();
		for (V value: iterable) {
			treeSet.add(value);
		}
		return treeSet;
	}

	/**
	 * Creates a new tree set and inserts the entries of the specified sorted
	 * set.
	 * <p>
	 * The comparator of the specified set is also used for the new set.
	 * <p>
	 * Unfortunately, this operation runs in O(n*log n), even though it could
	 * run in linear time; blame Sun.
	 */
	public static <V> TreeSet<V> createTreeSet(SortedSet<V> sortedSet) {

		return new TreeSet<>(sortedSet);
	}

	/**
	 * Creates a new tree set and inserts the specified values.
	 */
	@SafeVarargs
	public static <V> TreeSet<V> createTreeSetFrom(V...values) {

		return createTreeSet(Arrays.asList(values));
	}
}
