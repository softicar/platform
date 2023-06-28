package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A {@link NumberMap} for floats.
 *
 * @param <K>
 *            the type of the keys
 * @author Oliver Richers
 */
public class FloatMap<K> extends NumberMap<K, Float> {

	public FloatMap() {

		this(new TreeMap<>());
	}

	public FloatMap(Comparator<K> comparator) {

		this(new TreeMap<>(comparator));
	}

	public FloatMap(Map<K, Float> data) {

		super(data, Arithmetics.FLOAT);
	}
}
