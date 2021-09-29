package com.softicar.platform.db.runtime.field;

import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.fields.ISqlIntegerField;
import java.util.Collection;

public interface IDbIdField<R extends IDbObject<R>> extends IDbPrimitiveField<R, Integer>, ISqlIntegerField<R> {

	int getBits();

	boolean isUnsigned();

	ISqlBooleanExpression<R> isIn(Collection<R> rows);

	ISqlBooleanExpression<R> isNotIn(Collection<R> rows);

	ISqlBooleanExpression<R> isEqual(R row);

	ISqlBooleanExpression<R> isNotEqual(R row);
}
