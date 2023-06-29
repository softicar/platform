package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A {@link NumberMap} for doubles.
 *
 * @param <K>
 *            the type of the keys
 * @author Oliver Richers
 */
public class DoubleMap<K> extends NumberMap<K, Double> {

	public DoubleMap() {

		this(new TreeMap<>());
	}

	public DoubleMap(Comparator<K> comparator) {

		this(new TreeMap<>(comparator));
	}

	public DoubleMap(Map<K, Double> data) {

		super(data, Arithmetics.DOUBLE);
	}
}
