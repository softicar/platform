package com.softicar.platform.common.core.number;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * A class to map between {@link Number} and {@link BigDecimal}.
 *
 * @author Oliver Richers
 */
public class BigDecimalMapper<T extends Number> {

	public static final BigDecimalMapper<BigDecimal> BIG_DECIMAL = new BigDecimalMapper<>(Function.identity(), Function.identity());
	public static final BigDecimalMapper<Double> DOUBLE = new BigDecimalMapper<>(BigDecimal::valueOf, BigDecimal::doubleValue);
	public static final BigDecimalMapper<Float> FLOAT = new BigDecimalMapper<>(BigDecimal::valueOf, BigDecimal::floatValue);

	private final Function<T, BigDecimal> toMapper;
	private final Function<BigDecimal, T> fromMapper;

	public BigDecimalMapper(Function<T, BigDecimal> toMapper, Function<BigDecimal, T> fromMapper) {

		this.toMapper = toMapper;
		this.fromMapper = fromMapper;
	}

	public BigDecimal toBigDecimal(T value) {

		return toMapper.apply(value);
	}

	public T fromBigDecimal(BigDecimal value) {

		return fromMapper.apply(value);
	}
}
