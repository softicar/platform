package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A {@link NumberMap} for longs.
 * 
 * @param <K>
 *            the type of the keys
 * @author Oliver Richers
 */
public class LongMap<K> extends NumberMap<K, Long> {

	public LongMap() {

		this(new TreeMap<K, Long>());
	}

	public LongMap(Comparator<K> comparator) {

		this(new TreeMap<K, Long>(comparator));
	}

	public LongMap(Map<K, Long> data) {

		super(data, Arithmetics.LONG);
	}
}
