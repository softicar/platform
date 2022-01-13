package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * Defines a transient field with values of type {@link DayTime}.
 * <p>
 * Please note that null-values are not supported. If the values are optional,
 * use {@link AbstractTransientOptionalField}.
 *
 * @param <O>
 *            the type of the field owning object class
 * @author Thees Koester
 */
public abstract class AbstractTransientDayTimeField<O extends IDbTableRow<O, ?>> extends AbstractTransientObjectField<O, DayTime> {

	public AbstractTransientDayTimeField() {

		super(TransientFieldValueTypes.getDayTime());
	}
}
