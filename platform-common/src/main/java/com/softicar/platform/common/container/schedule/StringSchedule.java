package com.softicar.platform.common.container.schedule;

import com.softicar.platform.common.container.matrix.traits.StringMatrixTraits;
import java.util.Comparator;

/**
 * A {@link SimpleSchedule} for values of type {@link String}.
 *
 * @author Oliver Richers
 */
public class StringSchedule<R extends Comparable<? super R>, C extends Comparable<? super C>> extends SimpleSchedule<R, C, String> {

	public StringSchedule(C backorderColumn) {

		super(new StringMatrixTraits(), backorderColumn, null, null);
	}

	public StringSchedule(C backorderColumn, Comparator<R> rowComparator, Comparator<C> colComparator) {

		super(new StringMatrixTraits(), backorderColumn, rowComparator, colComparator);
	}
}
