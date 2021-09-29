package com.softicar.platform.common.container.schedule;

import com.softicar.platform.common.container.matrix.traits.IntegerMatrixTraits;
import java.util.Comparator;

/**
 * A {@link SimpleSchedule} for values of type {@link Integer}.
 *
 * @author Oliver Richers
 */
public class IntegerSchedule<R extends Comparable<? super R>, C extends Comparable<? super C>> extends SimpleSchedule<R, C, Integer> {

	public IntegerSchedule(C backorderColumn) {

		super(new IntegerMatrixTraits(), backorderColumn, null, null);
	}

	public IntegerSchedule(C backorderColumn, Comparator<R> rowComparator, Comparator<C> colComparator) {

		super(new IntegerMatrixTraits(), backorderColumn, rowComparator, colComparator);
	}
}
