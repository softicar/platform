package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.sql.statement.ISqlConditionalStatement;

public abstract class AbstractDbTableKey<R, P> implements IDbTableKey<R, P> {

	@Override
	public void addKeyCondition(ISqlConditionalStatement<R, ?> statement, P keyValue) {

		for (IDbField<R, ?> field: getFields()) {
			addConditionFromKey(statement, field, keyValue);
		}
	}

	private <V> void addConditionFromKey(ISqlConditionalStatement<R, ?> statement, IDbField<R, V> field, P keyValue) {

		statement.where(field.isEqual(getValue(field, keyValue)));
	}
}
