package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.fields.ISqlTimeField;

public interface IDbTimeField<R> extends IDbPrimitiveField<R, Time>, ISqlTimeField<R> {

	// nothing to add
}
