package com.softicar.platform.common.math.arithmetic;

import java.math.BigDecimal;

/**
 * Enumeration of arithmetics for basic types.
 *
 * @author Oliver Richers
 */
public class Arithmetics {

	public static final IArithmetic<BigDecimal> BIG_DECIMAL = new BigDecimalArithmetic();
	public static final IArithmetic<Double> DOUBLE = new DoubleArithmetic();
	public static final IArithmetic<Float> FLOAT = new FloatArithmetic();
	public static final IArithmetic<Integer> INTEGER = new IntegerArithmetic();
	public static final IArithmetic<Long> LONG = new LongArithmetic();
}
