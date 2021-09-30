package com.softicar.platform.common.container.list;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A list using {@link ArrayList} and {@link HashSet}.
 *
 * @author Oliver Richers
 */
public class HashList<T> extends AbstractList<T> {

	private final Set<T> set = new HashSet<>();
	private final List<T> list = new ArrayList<>();

	public HashList() {

		// nothing to do
	}

	public HashList(Collection<? extends T> elements) {

		addAll(elements);
	}

	@Override
	public boolean add(T element) {

		if (set.add(element)) {
			list.add(element);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public T remove(int index) {

		T element = list.remove(index);
		set.remove(element);
		return element;
	}

	@Override
	public boolean remove(Object element) {

		if (set.remove(element)) {
			list.remove(element);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public T get(int index) {

		return list.get(index);
	}

	@Override
	public int size() {

		return list.size();
	}

	@Override
	public boolean contains(Object element) {

		return set.contains(element);
	}

	public void reverse() {

		Collections.reverse(list);
	}
}
