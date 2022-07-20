package com.softicar.platform.emf.test.simple.scoped;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.emf.transaction.EmfTestTransaction;

public class EmfScopedTestObjectLog extends AbstractDbRecord<EmfScopedTestObjectLog, Tuple2<EmfScopedTestObject, EmfTestTransaction>> {

	// @formatter:off
	public static final DbTableBuilder<EmfScopedTestObjectLog, EmfScopedTestObjectLog, Tuple2<EmfScopedTestObject, EmfTestTransaction>> BUILDER = new DbTableBuilder<>("Test", "SimpleScopedEntityLog", EmfScopedTestObjectLog::new, EmfScopedTestObjectLog.class);
	public static final IDbForeignRowField<EmfScopedTestObjectLog, EmfScopedTestObject, Integer> SIMPLE_ENTITY = BUILDER.addForeignRowField("testEntityLog_simpleEntity", o -> o.simpleEntity, (o, v) -> o.simpleEntity = v, EmfScopedTestObject.ID).setNullable().setDefault(null);
	public static final IDbForeignField<EmfScopedTestObjectLog, EmfTestTransaction> TRANSACTION = BUILDER.addForeignField("testEntityLog_transaction", o -> o.transaction, (o, v) -> o.transaction = v, EmfTestTransaction.ID).setNullable().setDefault(null);
	public static final IDbStringField<EmfScopedTestObjectLog> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbBooleanField<EmfScopedTestObjectLog> ACTIVE = BUILDER.addBooleanField("active", o -> o.active, (o, v) -> o.active = v);
	public static final IDbTableKey<EmfScopedTestObjectLog, Tuple2<EmfScopedTestObject, EmfTestTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(SIMPLE_ENTITY, TRANSACTION));
	public static final DbRecordTable<EmfScopedTestObjectLog,Tuple2<EmfScopedTestObject, EmfTestTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	private EmfScopedTestObject simpleEntity;
	private EmfTestTransaction transaction;
	private String name;
	private Boolean active;

	public EmfScopedTestObject getSimpleEntity() {

		return SIMPLE_ENTITY.getValue(this);
	}

	public EmfTestTransaction getTransaction() {

		return TRANSACTION.getValue(this);
	}

	public String getName() {

		return getValue(NAME);
	}

	public Boolean isActive() {

		return getValue(ACTIVE);
	}

	@Override
	public DbRecordTable<EmfScopedTestObjectLog, Tuple2<EmfScopedTestObject, EmfTestTransaction>> table() {

		return TABLE;
	}
}
