package com.softicar.platform.db.sql.fields;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.expressions.date.ISqlTimeExpression1;
import com.softicar.platform.db.sql.field.ISqlValueField;

public interface ISqlTimeField<R> extends ISqlValueField<R, Time>, ISqlTimeExpression1<R> {

	// nothing to add
}
