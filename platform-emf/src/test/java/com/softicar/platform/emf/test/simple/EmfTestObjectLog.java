package com.softicar.platform.emf.test.simple;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.emf.transaction.EmfTestTransaction;

public class EmfTestObjectLog extends AbstractDbRecord<EmfTestObjectLog, Tuple2<EmfTestObject, EmfTestTransaction>> {

	// @formatter:off
	public static final DbTableBuilder<EmfTestObjectLog, EmfTestObjectLog, Tuple2<EmfTestObject, EmfTestTransaction>> BUILDER = new DbTableBuilder<>("Test", "SimpleEntityLog", EmfTestObjectLog::new, EmfTestObjectLog.class);
	public static final IDbForeignRowField<EmfTestObjectLog, EmfTestObject, Integer> SIMPLE_ENTITY = BUILDER.addForeignRowField("testEntityLog_simpleEntity", o -> o.simpleEntity, (o, v) -> o.simpleEntity = v, EmfTestObject.ID).setNullable().setDefault(null);
	public static final IDbForeignField<EmfTestObjectLog, EmfTestTransaction> TRANSACTION = BUILDER.addForeignField("testEntityLog_transaction", o -> o.transaction, (o, v) -> o.transaction = v, EmfTestTransaction.ID).setNullable().setDefault(null);
	public static final IDbStringField<EmfTestObjectLog> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbDayField<EmfTestObjectLog> DAY = BUILDER.addDayField("day", o -> o.day, (o, v) -> o.day = v).setNullable().setDefault(null);
	public static final IDbBooleanField<EmfTestObjectLog> ACTIVE = BUILDER.addBooleanField("active", o -> o.active, (o, v) -> o.active = v);
	public static final IDbTableKey<EmfTestObjectLog, Tuple2<EmfTestObject, EmfTestTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(SIMPLE_ENTITY, TRANSACTION));
	public static final DbRecordTable<EmfTestObjectLog,Tuple2<EmfTestObject, EmfTestTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	private EmfTestObject simpleEntity;
	private EmfTestTransaction transaction;
	private String name;
	private Day day;
	private Boolean active;

	public EmfTestObject getSimpleEntity() {

		return SIMPLE_ENTITY.getValue(this);
	}

	public EmfTestTransaction getTransaction() {

		return TRANSACTION.getValue(this);
	}

	public String getName() {

		return getValue(NAME);
	}

	public Day getDay() {

		return getValue(DAY);
	}

	public Boolean isActive() {

		return getValue(ACTIVE);
	}

	@Override
	public DbRecordTable<EmfTestObjectLog, Tuple2<EmfTestObject, EmfTestTransaction>> table() {

		return TABLE;
	}
}
