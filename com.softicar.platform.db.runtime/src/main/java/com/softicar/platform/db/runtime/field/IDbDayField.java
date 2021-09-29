package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.sql.fields.ISqlDayField;

public interface IDbDayField<R> extends IDbPrimitiveField<R, Day>, ISqlDayField<R> {

	/**
	 * Returns whether the default value of this field is {@link Day#today()}.
	 *
	 * @return <i>true</i> if today is the default; <i>false</i> otherwise
	 */
	boolean isDefaultToday();
}
