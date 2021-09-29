package com.softicar.platform.db.sql.fields;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.sql.expressions.date.ISqlDayExpression1;
import com.softicar.platform.db.sql.field.ISqlValueField;

public interface ISqlDayField<R> extends ISqlValueField<R, Day>, ISqlDayExpression1<R> {

	// nothing to add
}
