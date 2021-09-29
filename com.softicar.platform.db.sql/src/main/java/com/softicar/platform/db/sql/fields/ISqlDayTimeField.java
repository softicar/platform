package com.softicar.platform.db.sql.fields;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.db.sql.expressions.date.ISqlDayTimeExpression1;
import com.softicar.platform.db.sql.field.ISqlValueField;

public interface ISqlDayTimeField<R> extends ISqlValueField<R, DayTime>, ISqlDayTimeExpression1<R> {

	// nothing to add
}
