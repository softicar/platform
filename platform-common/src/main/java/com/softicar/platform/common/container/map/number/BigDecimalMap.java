package com.softicar.platform.common.container.map.number;

import com.softicar.platform.common.math.arithmetic.Arithmetics;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A {@link NumberMap} for {@link BigDecimal}.
 *
 * @param <K>
 *            the type of the keys
 * @author Oliver Richers
 */
public class BigDecimalMap<K> extends NumberMap<K, BigDecimal> {

	public BigDecimalMap() {

		this(new TreeMap<>());
	}

	public BigDecimalMap(Comparator<K> comparator) {

		this(new TreeMap<>(comparator));
	}

	public BigDecimalMap(Map<K, BigDecimal> data) {

		super(data, Arithmetics.BIG_DECIMAL);
	}

	public void round(int precision) {

		for (K key: getDataContainer().keySet()) {
			getDataContainer().put(key, getDataContainer().get(key).setScale(precision, RoundingMode.HALF_UP));
		}
	}
}
