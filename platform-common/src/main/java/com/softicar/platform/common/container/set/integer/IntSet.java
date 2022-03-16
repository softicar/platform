package com.softicar.platform.common.container.set.integer;

import com.softicar.platform.common.container.array.IntegerArrayAdapter;
import com.softicar.platform.common.container.map.integer.AbstractIntKeySet;
import com.softicar.platform.common.core.annotations.Nullable;
import java.util.Iterator;
import java.util.List;

/**
 * A very memory-efficient implementation of an integer hash set.
 * <p>
 * About 4 bytes are used for every entry, besides capacity overhead and the map
 * object itself. The only draw-back is, that you can not add the value
 * Integer.MIN_VALUE to the set, because it's internally used to represent the
 * null-value.
 *
 * @author Oliver Richers
 */
public class IntSet extends AbstractIntKeySet<Integer> implements Iterable<Integer> {

	private int[] values = new int[AbstractIntKeySet.MIN_CAPACITY];

	// ********************************************************************************
	// //
	// * Interface * //
	// ********************************************************************************
	// //

	public final Integer add(Integer value) {

		return super._add(value);
	}

	public final Integer get(int key) {

		return super._get(key);
	}

	public final Integer remove(int key) {

		return super._remove(key);
	}

	public final double getStepAverage() {

		return super._getStepAverage();
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
	public final Iterator<Integer> iterator() {

		return super._iterator();
	}

	// ********************************************************************************
	// //
	// * Implementation of base functions * //
	// ********************************************************************************
	// //

	@Override
	protected final int getKey(Integer value) {

		return value;
	}

	@Override
	protected final int getCapacity() {

		return values.length;
	}

	@Override
	protected final Integer getValue(int index) {

		return getValue(values, index);
	}

	@Override
	protected final List<Integer> getValues() {

		return IntegerArrayAdapter.get(values);
	}

	@Override
	protected final void resize(int capacity) {

		this.values = new int[capacity];
	}

	@Override
	protected final void setValue(int index, @Nullable Integer value) {

		this.values[index] = value == null? 0 : value - Integer.MIN_VALUE;
	}

	static Integer getValue(int[] array, int index) {

		int tmp = array[index];
		return tmp == 0? null : Integer.MIN_VALUE + tmp;
	}
}
