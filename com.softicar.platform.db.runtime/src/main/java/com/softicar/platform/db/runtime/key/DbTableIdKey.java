package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;

public class DbTableIdKey<R> extends DbTableKey1<R, Integer> {

	public DbTableIdKey(IDbField<R, Integer> idField) {

		super(idField);
	}

	@Override
	public boolean isGenerated() {

		return true;
	}

	@Override
	public IDbField<R, Integer> getIdField() {

		return field;
	}
}
