package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.fields.ISqlDayTimeField;

public interface IDbDayTimeField<R> extends IDbPrimitiveField<R, DayTime>, ISqlDayTimeField<R> {

	/**
	 * Returns whether the default value of this field is {@link DayTime#now()}.
	 *
	 * @return <i>true</i> if now is the default; <i>false</i> otherwise
	 */
	boolean isDefaultNow();

	/**
	 * Returns whether this field is assigned the current timestamp whenever the
	 * table row is updated.
	 *
	 * @return <i>true</i> if this field is automatically updated to the current
	 *         point in time; <i>false</i> otherwise
	 */
	boolean isOnUpdateNow();

	/**
	 * Returns whether this field is based on the <b>DATETIME</b> or
	 * <b>TIMESTAMP</b> type.
	 *
	 * @return <i>true</i> if this field is based on <b>TIMESTAMP</b>;
	 *         <i>false</i> otherwise
	 */
	boolean isTimestamp();
}
