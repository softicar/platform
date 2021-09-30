package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

public class DbTinyTestRecord extends AbstractDbRecord<DbTinyTestRecord, Integer> {

	// @formatter:off
	public static final DbTableBuilder<DbTinyTestRecord, DbTinyTestRecord, Integer> BUILDER = new DbTableBuilder<>("db", "tiny", DbTinyTestRecord::new, DbTinyTestRecord.class);
	public static final IDbIntegerField<DbTinyTestRecord> KEY = BUILDER.addIntegerField("key", r -> r.key, (r, v) -> r.key = v);
	public static final IDbIntegerField<DbTinyTestRecord> VALUE = BUILDER.addIntegerField("value", r -> r.value, (r, v) -> r.value = v).setDefault(0);
	public static final IDbTableKey<DbTinyTestRecord, Integer> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(KEY));
	public static final DbRecordTable<DbTinyTestRecord, Integer> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	private Integer key;
	private Integer value;

	public Integer getKey() {

		return getValue(KEY);
	}

	public Integer getValue() {

		return getValue(VALUE);
	}

	public DbTinyTestRecord setValue(Integer value) {

		return setValue(VALUE, value);
	}

	@Override
	public DbRecordTable<DbTinyTestRecord, Integer> table() {

		return TABLE;
	}
}
