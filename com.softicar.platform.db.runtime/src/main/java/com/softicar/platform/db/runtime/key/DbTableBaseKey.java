package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.field.IDbBaseField;

public class DbTableBaseKey<R, B extends IDbEntity<B, P>, P> extends DbTableKey1<R, B> {

	public DbTableBaseKey(IDbBaseField<R, B, P> baseField) {

		super(baseField);
	}

	@Override
	public boolean isGenerated() {

		return false;
	}

	@Override
	public boolean isBase() {

		return true;
	}
}
