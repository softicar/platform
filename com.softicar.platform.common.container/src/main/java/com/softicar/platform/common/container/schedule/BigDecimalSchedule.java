package com.softicar.platform.common.container.schedule;

import com.softicar.platform.common.container.matrix.traits.BigDecimalMatrixTraits;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 * A {@link SimpleSchedule} for values of type {@link BigDecimal}.
 *
 * @author Oliver Richers
 */
public class BigDecimalSchedule<R extends Comparable<? super R>, C extends Comparable<? super C>> extends SimpleSchedule<R, C, BigDecimal> {

	public BigDecimalSchedule(C backorderColumn) {

		super(new BigDecimalMatrixTraits(), backorderColumn, null, null);
	}

	public BigDecimalSchedule(C backorderColumn, Comparator<R> rowComparator, Comparator<C> colComparator) {

		super(new BigDecimalMatrixTraits(), backorderColumn, rowComparator, colComparator);
	}
}
