package com.softicar.platform.common.container.map.integer;

import com.softicar.platform.common.core.annotations.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A very memory-efficient implementation of a hashing container.
 * <p>
 * This is the base class of {@link IntKeySet}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractIntKeySet<V> {

	protected static final int MIN_CAPACITY = 4;
	private static final int DEFAULT_MAX_LOAD_PERCENTAGE = 75;

	private final int maxLoadPercentage;
	private int _size = 0;

	public AbstractIntKeySet() {

		this(DEFAULT_MAX_LOAD_PERCENTAGE);
	}

	public AbstractIntKeySet(int maxLoadPercentage) {

		this.maxLoadPercentage = maxLoadPercentage;
	}

	// -------------------- INTERFACE -------------------- //

	/**
	 * Adds the specified value to the map.
	 *
	 * @param value
	 *            the value to add
	 * @return the previously stored value or null
	 */
	protected final V _add(V value) {

		int index = findSlot(getKey(value));
		V oldValue = getValue(index);
		if (oldValue != null) {
			// overwrite the old value
			setValue(index, value);
		} else {
			// rebuild the table if load limit reached
			if (needGrow(_size + 1)) {
				rebuild(2 * getCapacity());
				index = findSlot(getKey(value));
			}

			_size += 1;
			setValue(index, value);
		}
		return oldValue;
	}

	protected final V _get(int key) {

		int index = findSlot(key);
		return getValue(index);
	}

	protected final V _remove(int key) {

		// find slot of the key
		int i = findSlot(key);
		V removedValue = getValue(i);
		if (removedValue != null) {
			if (needShrink(_size - 1)) {
				// if we need to shrink the table, we just set the value to null
				// and rebuild the table
				setValue(i, null);
				rebuild(getCapacity() / 2);
			} else {
				// we have to move subsequent values
				int j = i;
				while (true) {
					// no more subsequent values?
					j = (j + 1) & (getCapacity() - 1);
					V value = getValue(j);
					if (value == null) {
						break;
					}

					int k = getPrimarySlot(getKey(value));
					if ((j > i && (k <= i || k > j)) || (j < i && (k <= i && k > j))) {
						setValue(i, value);
						i = j;
					}
				}

				// free the slot
				setValue(i, null);
				_size -= 1;
			}
		}
		return removedValue;
	}

	protected final double _getStepAverage() {

		int totalDistance = 0;
		Iterator<V> iterator = _iterator();
		while (iterator.hasNext()) {
			int key = getKey(iterator.next());
			int primary = getPrimarySlot(key);
			int slot = findSlot(key);
			int distance = slot - primary;
			if (distance < 0) {
				distance += getCapacity();
			}
			totalDistance += distance;
		}
		return _size > 0? totalDistance / (double) _size : 0.0;
	}

	protected final double _getClusterAverage() {

		int totalClusterSize = 0;
		int clusterCount = 0;
		int clusterSize = 0;
		for (int i = 0; i < getCapacity(); ++i) {
			if (getValue(i) == null) {
				if (clusterSize > 0) {
					totalClusterSize += clusterSize;
					++clusterCount;
				}
				clusterSize = 0;
			} else {
				++clusterSize;
			}
		}
		if (clusterSize > 0) {
			totalClusterSize += clusterSize;
			++clusterCount;
		}
		return totalClusterSize / (double) clusterCount;
	}

	protected final int _size() {

		return _size;
	}

	protected final String _toString() {

		int i = 0;
		StringBuilder builder = new StringBuilder("[");
		for (V value: getValues()) {
			if (value != null) {
				builder.append(getKey(value)).append(": " + value);
				if (++i < _size) {
					builder.append(", ");
				}
			}
		}
		builder.append("]");
		return builder.toString();
	}

	protected final Iterator<V> _iterator() {

		return new Iter();
	}

	// -------------------- ABSTRACT METHODS -------------------- //

	protected abstract int getKey(V value);

	protected abstract void setValue(int index, @Nullable V value);

	protected abstract V getValue(int index);

	protected abstract List<V> getValues();

	protected abstract int getCapacity();

	protected abstract void resize(int capacity);

	// -------------------- PRIVATE -------------------- //

	private final int findSlot(int key) {

		assert (_size < getCapacity());

		int index = getPrimarySlot(key);
		while (true) {
			// empty slot?
			V value = getValue(index);
			if (value == null) {
				return index;
			}

			// matching slot?
			if (getKey(value) == key) {
				return index;
			}

			// try next slot
			index = (index + 1) & (getCapacity() - 1);
		}
	}

	private final int getPrimarySlot(int key) {

		return getHash(key) & (getCapacity() - 1);
	}

	private final boolean needGrow(int newSize) {

		int capacity = getCapacity();
		return (newSize * 100L / capacity) > this.maxLoadPercentage || newSize >= capacity;
	}

	private final boolean needShrink(int newSize) {

		int capacity = getCapacity();
		return (newSize * 100L / (capacity / 4)) <= this.maxLoadPercentage && capacity > MIN_CAPACITY;
	}

	private final void rebuild(int newCapacity) {

		// allocate new value array
		int oldCapacity = getCapacity();
		Iterable<V> oldValues = getValues();
		resize(newCapacity);
		_size = 0;

		// add values to new array
		for (V value: oldValues) {
			if (value != null) {
				_add(value);
			}
		}

		com.softicar.platform.common.core.logging.Log.fverbose("WeakIntHashMap rebuilded: size: %s capacity: %s->%s", _size, oldCapacity, getCapacity());
	}

	private static int getHash(int hash) {

		int result = 0;

		for (int i = 0; i < 4; ++i) {
			result += (hash >> (i * 8)) & 255;
			result += (result << 10);
			result ^= (result >> 6);
		}

		result += (result << 3);
		result ^= (result >> 11);
		result += (result << 15);
		return result;

//		hash ^= (hash >>> 20) ^ (hash >>> 12);
//		return hash ^ (hash >>> 7) ^ (hash >>> 4);
	}

	private final class Iter implements Iterator<V> {

		public Iter() {

			findNext();
		}

		@Override
		public final boolean hasNext() {

			return _index < getCapacity();
		}

		@Override
		public final V next() {

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			V value = getValue(_index);
			findNext();
			return value;
		}

		@Override
		public final void remove() {

			throw new UnsupportedOperationException();
		}

		private final void findNext() {

			++_index;
			while (_index < getCapacity() && getValue(_index) == null) {
				++_index;
			}
		}

		private int _index = -1;
	}
}
