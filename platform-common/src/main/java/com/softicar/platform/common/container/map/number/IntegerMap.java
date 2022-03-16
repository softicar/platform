package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A {@link NumberMap} for integers.
 * 
 * @param <K>
 *            the type of the keys
 * @author Oliver Richers
 */
public class IntegerMap<K> extends NumberMap<K, Integer> {

	public IntegerMap() {

		this(new TreeMap<K, Integer>());
	}

	public IntegerMap(Comparator<K> comparator) {

		this(new TreeMap<K, Integer>(comparator));
	}

	public IntegerMap(Map<K, Integer> data) {

		super(data, Arithmetics.INTEGER);
	}
}
