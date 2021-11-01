package com.softicar.platform.emf.test;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.record.IDbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.transaction.EmfTestTransaction;

public class EmfTestSubObjectLog extends AbstractDbRecord<EmfTestSubObjectLog, Tuple2<EmfTestSubObject, EmfTestTransaction>> {

	// @formatter:off
	public static final DbTableBuilder<EmfTestSubObjectLog, EmfTestSubObjectLog, Tuple2<EmfTestSubObject, EmfTestTransaction>> BUILDER = new DbTableBuilder<>("Test", "EntityLog", EmfTestSubObjectLog::new, EmfTestSubObjectLog.class);
	public static final IDbForeignRowField<EmfTestSubObjectLog, EmfTestSubObject, EmfTestObject> SUB_OBJECT = BUILDER.addForeignRowField("entity", o -> o.subObject, (o, v) -> o.subObject = v, EmfTestSubObject.SIMPLE_ENTITY).setNullable().setDefault(null);
	public static final IDbForeignField<EmfTestSubObjectLog, EmfTestTransaction> TRANSACTION = BUILDER.addForeignField("entityLog_transaction", o -> o.transaction, (o, v) -> o.transaction = v, EmfTestTransaction.ID).setNullable().setDefault(null);
	public static final IDbStringField<EmfTestSubObjectLog> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbIntegerField<EmfTestSubObjectLog> VALUE = BUILDER.addIntegerField("value", o -> o.value, (o, v) -> o.value = v).setNullable().setDefault(null);
	public static final IDbIntegerField<EmfTestSubObjectLog> NOT_NULLABLE_VALUE = BUILDER.addIntegerField("notNullableValue", o -> o.notNullableValue, (o, v) -> o.notNullableValue = v);
	public static final IDbTableKey<EmfTestSubObjectLog, Tuple2<EmfTestSubObject, EmfTestTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(SUB_OBJECT, TRANSACTION));
	public static final DbRecordTable<EmfTestSubObjectLog,Tuple2<EmfTestSubObject, EmfTestTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	private EmfTestSubObject subObject;
	private EmfTestTransaction transaction;
	private String name;
	private Integer value;
	private Integer notNullableValue;

	public EmfTestSubObject getSubObject() {

		return SUB_OBJECT.getValue(this);
	}

	public EmfTestTransaction getTransaction() {

		return TRANSACTION.getValue(this);
	}

	public String getName() {

		return NAME.getValue(this);
	}

	public Integer getValue() {

		return VALUE.getValue(this);
	}

	public EmfTestSubObjectLog setNotNullableValue(Integer notNullableValue) {

		return setValue(NOT_NULLABLE_VALUE, notNullableValue);
	}

	public Integer getNotNullableValue() {

		return getValue(NOT_NULLABLE_VALUE);
	}

	@Override
	public IDbRecordTable<EmfTestSubObjectLog, Tuple2<EmfTestSubObject, EmfTestTransaction>> table() {

		return TABLE;
	}
}
