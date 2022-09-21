package com.softicar.platform.db.runtime.object.sub;

import com.softicar.platform.db.runtime.entity.IDbEntity;
import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.DbTableRowInitializer;

public class DbSubObjectInitializer<R extends AbstractDbSubObject<R, B>, B extends IDbEntity<B, ?>> extends DbTableRowInitializer<R, B>
		implements IDbSubObjectInitializer<R, B> {

	public DbSubObjectInitializer(R row) {

		super(row);
	}

	@Override
	public R initializeCopy(R source) {

		initializeFlags(DbTableRowFlag.IMPERMANENT);
		initializeDataFields(source);
		initializePkFields(source.pk().copy());
		return row;
	}

	@Override
	public final R initializeWithBase(B base) {

		initializeFlags(DbTableRowFlag.IMPERMANENT);
		initializeAllFieldsToDefault(table.getDataFields());
		initializePkFields(base);
		return row;
	}
}
