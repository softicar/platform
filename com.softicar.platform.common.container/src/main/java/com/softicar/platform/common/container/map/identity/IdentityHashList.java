package com.softicar.platform.common.container.map.identity;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * A list using {@link ArrayList} and {@link IdentityHashMap}.
 *
 * @author Oliver Richers
 */
public class IdentityHashList<T> extends AbstractList<T> {

	private final Map<T, T> map;
	private final List<T> list;

	public IdentityHashList() {

		this.map = new IdentityHashMap<>();
		this.list = new ArrayList<>();
	}

	@Override
	public boolean add(T element) {

		if (map.put(element, element) == null) {
			list.add(element);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public T remove(int index) {

		T element = list.remove(index);
		map.remove(element);
		return element;
	}

	@Override
	public boolean remove(Object element) {

		if (map.remove(element) != null) {
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

		return map.containsKey(element);
	}

	public void reverse() {

		Collections.reverse(list);
	}
}
