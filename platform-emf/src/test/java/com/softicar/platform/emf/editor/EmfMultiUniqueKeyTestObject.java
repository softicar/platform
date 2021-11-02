package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.object.AbstractEmfObject;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;

public class EmfMultiUniqueKeyTestObject extends AbstractEmfObject<EmfMultiUniqueKeyTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfMultiUniqueKeyTestObject, EmfMultiUniqueKeyTestObject> BUILDER = new DbObjectTableBuilder<>("Test", "UniqueKeyEntity", EmfMultiUniqueKeyTestObject::new, EmfMultiUniqueKeyTestObject.class);
	public static final IDbIdField<EmfMultiUniqueKeyTestObject> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<EmfMultiUniqueKeyTestObject> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable();
	public static final IDbIntegerField<EmfMultiUniqueKeyTestObject> VALUE = BUILDER.addIntegerField("value", o -> o.value, (o, v) -> o.value = v).setNullable();
	public static final IDbKey<EmfMultiUniqueKeyTestObject> UK_NAME = BUILDER.addUniqueKey("name", NAME);
	public static final IDbKey<EmfMultiUniqueKeyTestObject> UK_VALUE = BUILDER.addUniqueKey("value", VALUE);
	public static final EmfObjectTable<EmfMultiUniqueKeyTestObject, EmfTestModuleInstance> TABLE = new EmfObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private String name;
	private Integer value;

	public EmfMultiUniqueKeyTestObject setName(String name) {

		return setValue(NAME, name);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getValue(NAME));
	}

	public EmfMultiUniqueKeyTestObject setValue(Integer value) {

		return setValue(VALUE, value);
	}

	public Integer getValue() {

		return getValue(VALUE);
	}

	@Override
	public EmfObjectTable<EmfMultiUniqueKeyTestObject, EmfTestModuleInstance> table() {

		return TABLE;
	}
}
