package com.softicar.platform.common.container.map.integer;

import com.softicar.platform.common.container.map.weak.WeakIntHashMap;
import com.softicar.platform.common.container.set.integer.IntSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A very memory-efficient implementation of a hashing container.
 * <p>
 * This class is an hybrid between set and a map. All contained values must have
 * a unique inherent key. For example, suppose you have a tree, where every tree
 * node has a unique integer identifier. The {@link IntKeySet} is a very
 * memory-efficient container for such nodes, because it will only store the
 * references to the tree nodes, an no additional keys of type {@link Integer}.
 * <p>
 * This class is abstract because you must implement the {@link #getKey(Object)}
 * method that extracts the key from a value.
 *
 * @see IntSet
 * @see WeakIntHashMap
 * @author Oliver Richers
 */
public abstract class IntKeySet<V> extends AbstractIntKeySet<V> implements Iterable<V> {

	public IntKeySet() {

		super();
	}

	public IntKeySet(int maxLoadPercentage) {

		super(maxLoadPercentage);
	}

	// -------------------- INTERFACE -------------------- //

	public final V add(V value) {

		return super._add(value);
	}

	public final V get(int key) {

		return super._get(key);
	}

	public final V remove(int key) {

		return super._remove(key);
	}

	public final double getStepAverage() {

		return super._getStepAverage();
	}

	public final double getClusterAverage() {

		return super._getClusterAverage();
	}

	public final int size() {

		return super._size();
	}

	public final boolean isEmpty() {

		return super._size() == 0;
	}

	@Override
	public final String toString() {

		return super._toString();
	}

	@Override
	public final Iterator<V> iterator() {

		return super._iterator();
	}

	@Override
	protected abstract int getKey(V value);

	// -------------------- IMPLEMENTED ABSTRACT METHODS -------------------- //

	@Override
	protected final void setValue(int index, V value) {

		_values[index] = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected final V getValue(int index) {

		return (V) _values[index];
	}

	@SuppressWarnings("unchecked")
	@Override
	protected final List<V> getValues() {

		return (List<V>) Arrays.asList(_values);
	}

	@Override
	protected final int getCapacity() {

		return _values.length;
	}

	@Override
	protected final void resize(int capacity) {

		_values = new Object[capacity];
	}

	// -------------------- PRIVATE -------------------- //

	private Object[] _values = new Object[MIN_CAPACITY];
}
