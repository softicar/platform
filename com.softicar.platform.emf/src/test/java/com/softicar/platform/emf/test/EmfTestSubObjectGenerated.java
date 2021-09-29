package com.softicar.platform.emf.test;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.transaction.EmfTestTransaction;

public abstract class EmfTestSubObjectGenerated extends AbstractDbSubObject<EmfTestSubObject, EmfTestObject> {

	// @formatter:off
	public static final DbSubObjectTableBuilder<EmfTestSubObject, EmfTestSubObjectGenerated, EmfTestObject, Integer> BUILDER = new DbSubObjectTableBuilder<>("Test", "Entity", EmfTestSubObject::new, EmfTestSubObject.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Sub-Object"));
		BUILDER.setTitle(IDisplayString.create("Test Sub-Objects"));
	}
	public static final IDbBaseField<EmfTestSubObject, EmfTestObject, Integer> SIMPLE_ENTITY = BUILDER.addBaseField("simpleEntity", o->o.simpleEntity, (o,v)->o.simpleEntity=v, EmfTestObject.TABLE);
	public static final IDbForeignField<EmfTestSubObject, EmfTestTransaction> TRANSACTION = BUILDER.addForeignField("entity_transaction", o->o.transaction, (o,v)->o.transaction=v, EmfTestTransaction.ID).setNullable().setDefault(null);
	public static final IDbStringField<EmfTestSubObject> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbIntegerField<EmfTestSubObject> VALUE = BUILDER.addIntegerField("value", o -> o.value, (o, v) -> o.value = v).setNullable().setDefault(null);
	public static final IDbIntegerField<EmfTestSubObject> NOT_NULLABLE_VALUE = BUILDER.addIntegerField("notNullableValue", o -> o.notNullableValue, (o, v) -> o.notNullableValue = v);
	public static final EmfTestSubObjectTable TABLE = new EmfTestSubObjectTable(BUILDER);
	// @formatter:on

	private EmfTestObject simpleEntity;
	private EmfTestTransaction transaction;
	private String name;
	private Integer value;
	private Integer notNullableValue;

	public EmfTestObject getSimpleEntity() {

		return getValue(SIMPLE_ENTITY);
	}

	public EmfTestSubObject setSimpleEntity(EmfTestObject simpleEntity) {

		return setValue(SIMPLE_ENTITY, simpleEntity);
	}

	public EmfTestSubObject setName(String name) {

		return setValue(NAME, name);
	}

	public String getName() {

		return getValue(NAME);
	}

	public EmfTestSubObject setValue(Integer value) {

		return setValue(VALUE, value);
	}

	public Integer getValue() {

		return getValue(VALUE);
	}

	public EmfTestSubObject setNotNullableValue(Integer notNullableValue) {

		return setValue(NOT_NULLABLE_VALUE, notNullableValue);
	}

	public Integer getNotNullableValue() {

		return getValue(NOT_NULLABLE_VALUE);
	}

	public String getDisplayName() {

		return name;
	}

	@Override
	public EmfTestSubObjectTable table() {

		return TABLE;
	}
}
