package com.softicar.platform.common.container.schedule;

import com.softicar.platform.common.container.matrix.traits.LongMatrixTraits;
import java.util.Comparator;

/**
 * A {@link SimpleSchedule} for values of type {@link Long}.
 *
 * @author Oliver Richers
 */
public class LongSchedule<R extends Comparable<R>, C extends Comparable<C>> extends SimpleSchedule<R, C, Long> {

	public LongSchedule(C backorderColumn) {

		super(new LongMatrixTraits(), backorderColumn, null, null);
	}

	public LongSchedule(C backorderColumn, Comparator<R> rowComparator, Comparator<C> colComparator) {

		super(new LongMatrixTraits(), backorderColumn, rowComparator, colComparator);
	}
}
