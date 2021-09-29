package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.structure.key.DbKeyType;
import java.util.Collections;
import java.util.List;

public class DbKey1<R, V> extends AbstractDbKey<R> implements IDbKey1<R, V> {

	private final IDbField<R, V> field;

	public DbKey1(DbKeyType type, String name, IDbField<R, V> field) {

		super(type, name);

		this.field = field;
	}

	@Override
	public List<IDbField<R, ?>> getFields() {

		return Collections.singletonList(field);
	}

	@Override
	public IDbField<R, V> getField() {

		return field;
	}
}
