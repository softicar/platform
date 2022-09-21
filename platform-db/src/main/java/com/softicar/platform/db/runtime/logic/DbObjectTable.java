package com.softicar.platform.db.runtime.logic;

import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.db.runtime.table.DbTable;

/**
 * Default implementation of {@link IDbObjectTable}.
 *
 * @author Oliver Richers
 */
public class DbObjectTable<R extends IDbObject<R>> extends DbTable<R, Integer> implements IDbObjectTable<R> {

	private final IDbIdField<R> idField;

	public DbObjectTable(IDbObjectTableBuilder<R> builder) {

		super(builder);

		this.idField = builder.getIdField();
	}

	@Override
	public IDbIdField<R> getPrimaryKeyField() {

		return idField;
	}
}
