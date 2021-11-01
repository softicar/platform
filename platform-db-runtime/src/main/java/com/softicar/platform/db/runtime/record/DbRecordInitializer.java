package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.runtime.table.row.DbTableRowFlag;
import com.softicar.platform.db.runtime.table.row.DbTableRowInitializer;

public class DbRecordInitializer<R extends AbstractDbRecord<R, P>, P> extends DbTableRowInitializer<R, P> implements IDbRecordInitializer<R, P> {

	public DbRecordInitializer(R row) {

		super(row);
	}

	@Override
	public R initializeToDefaults(P primaryKey) {

		initializeFlags(DbTableRowFlag.IMPERMANENT);
		initializeAllFieldsToDefault(table.getDataFields());
		initializePkFields(primaryKey);
		return row;
	}

	@Override
	public R initializeFullCopy(R record, DbTableRowFlag...flagsToEnable) {

		initializeFlags(flagsToEnable);
		initializePkFields(record.pk());
		initializeDataFields(record);
		return row;
	}
}
