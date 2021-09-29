package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.structure.key.DbKeyType;
import java.util.List;

public class DbKey<R> extends AbstractDbKey<R> {

	private final List<IDbField<R, ?>> fields;

	public DbKey(DbKeyType type, String name, List<IDbField<R, ?>> fields) {

		super(type, name);

		this.fields = fields;
	}

	@Override
	public List<IDbField<R, ?>> getFields() {

		return fields;
	}
}
