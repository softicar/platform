package com.softicar.platform.db.sql.fields;

import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpression1;
import com.softicar.platform.db.sql.field.ISqlValueField;

public interface ISqlBooleanField<R> extends ISqlValueField<R, Boolean>, ISqlBooleanExpression1<R> {

	// nothing to add
}
