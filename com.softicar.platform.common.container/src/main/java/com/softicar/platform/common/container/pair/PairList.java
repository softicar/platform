package com.softicar.platform.common.container.pair;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * A simple List of Pairs of objects.
 * <p>
 * Can be useful in scenarios where a Map is not suitable to store many
 * associations between objects, e.g. when the keys are mutable, which would
 * destroy the semantics of regular maps. Can be converted to a Map in O(n * log
 * n) at any time.
 *
 * @author Alexander Schmidt
 */
public class PairList<A extends Comparable<?>, B> extends ArrayList<Pair<A, B>> {

	private static final long serialVersionUID = 1L;

	public void add(A first, B second) {

		add(new Pair<>(first, second));
	}

	public void addAll(Map<A, B> map) {

		for (Entry<A, B> entry: map.entrySet()) {
			add(new Pair<>(entry.getKey(), entry.getValue()));
		}
	}

	public void addAll(PairList<A, B> other) {

		for (Pair<A, B> pair: other) {
			add(pair);
		}
	}

	/**
	 * @return A List of all first values occurring in the Pairs of this
	 *         {@link PairList} (repetitions possible).
	 */
	public List<A> getAllFirst() {

		List<A> allFirst = new ArrayList<>();
		for (Pair<A, B> pair: this) {
			allFirst.add(pair.getFirst());
		}
		return allFirst;
	}

	/**
	 * @return A List of all second values occurring in the Pairs of this
	 *         {@link PairList} (repetitions possible).
	 */
	public List<B> getAllSecond() {

		List<B> allSecond = new ArrayList<>();
		for (Pair<A, B> pair: this) {
			allSecond.add(pair.getSecond());
		}
		return allSecond;
	}

	/**
	 * Performs a linear search on the list to check whether there is a pair
	 * with the given value as its first value.
	 * <p>
	 * In case a linear search is not desired,
	 * {@link #createNewTreeMap(boolean)} and {@link Map#get(Object)} can be
	 * used for a search in O(log n). However, the Map creation itself has a
	 * complexity of O(n * log n).
	 *
	 * @param first
	 * @return True if the {@link PairList} contains pair with the given value
	 *         as its first value. False otherwise.
	 */
	public boolean containsFirst(A first) {

		for (Pair<A, B> pair: this) {
			A existingFirst = pair.getFirst();

			if (first == null) {
				if (existingFirst == null) {
					return true;
				}
			} else {
				if (first.equals(existingFirst)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @param throwOnDuplicateKey
	 * @return A new TreeMap created from the entries of this {@link PairList}.
	 */
	public TreeMap<A, B> createNewTreeMap(boolean throwOnDuplicateKey) {

		TreeMap<A, B> map = new TreeMap<>();

		for (Pair<A, B> pair: this) {
			A first = pair.getFirst();
			B second = pair.getSecond();

			if (throwOnDuplicateKey && map.containsKey(first)) {
				throw new SofticarDeveloperException("Duplicate keys are not allowed.");
			}

			map.put(first, second);
		}

		return map;
	}

	/**
	 * @return A new {@link ListTreeMap} created from the entries of this
	 *         {@link PairList}.
	 */
	public ListTreeMap<A, B> createNewListTreeMap() {

		ListTreeMap<A, B> map = new ListTreeMap<>();

		for (Pair<A, B> pair: this) {
			map.addToList(pair.getFirst(), pair.getSecond());
		}

		return map;
	}
}
