package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.utils.CastUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class TransientFieldValueTypes {

	// ------------------------------ comparable ------------------------------ //

	public static final <V extends Comparable<V>> ITransientFieldValueType<V> getComparable(Class<V> valueClass) {

		return new TransientFieldValueType<>(valueClass, V::compareTo);
	}

	// ------------------------------ primitive types ------------------------------ //

	public static final ITransientFieldValueType<Boolean> getBoolean() {

		return getComparable(Boolean.class);
	}

	public static final ITransientFieldValueType<BigDecimal> getBigDecimal() {

		return getComparable(BigDecimal.class);
	}

	public static final ITransientFieldValueType<Double> getDouble() {

		return getComparable(Double.class);
	}

	public static final ITransientFieldValueType<Integer> getInteger() {

		return getComparable(Integer.class);
	}

	public static final ITransientFieldValueType<Long> getLong() {

		return getComparable(Long.class);
	}

	public static final ITransientFieldValueType<String> getString() {

		return getComparable(String.class);
	}

	// ------------------------------ collections ------------------------------ //

	public static final <V> ITransientFieldValueType<List<V>> getList() {

		return new TransientFieldValueType<>(CastUtils.cast(List.class));
	}

	public static final <V> ITransientFieldValueType<Set<V>> getSet() {

		return new TransientFieldValueType<>(CastUtils.cast(Set.class));
	}

	public static final <K, V> ITransientFieldValueType<Map<K, V>> getMap() {

		return new TransientFieldValueType<>(CastUtils.cast(Map.class));
	}

	// ------------------------------ special ------------------------------ //

	public static final <V> ITransientFieldValueType<Optional<V>> getOptional() {

		return new TransientFieldValueType<>(CastUtils.cast(Optional.class));
	}
}
