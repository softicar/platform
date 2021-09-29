package com.softicar.platform.db.runtime.record;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

public class DbTestRecord extends AbstractDbRecord<DbTestRecord, Tuple2<String, Integer>> {

	// @formatter:off
	public static final DbTableBuilder<DbTestRecord, DbTestRecord, Tuple2<String, Integer>> BUILDER = new DbTableBuilder<>("db", "foo", DbTestRecord::new, DbTestRecord.class);
	public static final IDbStringField<DbTestRecord> NAME = BUILDER.addStringField("name", r -> r.name, (r, v) -> r.name = v).setMaximumLength(255);
	public static final IDbIntegerField<DbTestRecord> INDEX = BUILDER.addIntegerField("index", r -> r.index, (r, v) -> r.index = v);
	public static final IDbStringField<DbTestRecord> ADDRESS = BUILDER.addStringField("address", r -> r.address, (r, v) -> r.address = v).setMaximumLength(255).setDefault("");
	public static final IDbTableKey<DbTestRecord, Tuple2<String, Integer>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(NAME, INDEX));
	public static final DbRecordTable<DbTestRecord, Tuple2<String, Integer>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	private String name;
	private Integer index;
	private String address;

	@Override
	public DbRecordTable<DbTestRecord, Tuple2<String, Integer>> table() {

		return TABLE;
	}

	public String getName() {

		return NAME.getValue(this);
	}

	public Integer getIndex() {

		return INDEX.getValue(this);
	}

	public String getAddress() {

		return ADDRESS.getValue(this);
	}

	public void setAddress(String address) {

		ADDRESS.setValue(this, address);
	}
}
