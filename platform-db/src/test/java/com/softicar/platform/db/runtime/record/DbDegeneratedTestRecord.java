package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

public class DbDegeneratedTestRecord extends AbstractDbRecord<DbDegeneratedTestRecord, Integer> {

	// @formatter:off
	public static final DbTableBuilder<DbDegeneratedTestRecord, DbDegeneratedTestRecord, Integer> BUILDER = new DbTableBuilder<>("db", "generated", DbDegeneratedTestRecord::new, DbDegeneratedTestRecord.class);
	public static final IDbIntegerField<DbDegeneratedTestRecord> ID = BUILDER.addIntegerField("id", r -> r.id, (r, v) -> r.id = v);
	public static final IDbTableKey<DbDegeneratedTestRecord, Integer> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(ID));
	public static final DbRecordTable<DbDegeneratedTestRecord, Integer> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	private Integer id;

	@Override
	public DbRecordTable<DbDegeneratedTestRecord, Integer> table() {

		return TABLE;
	}
}
