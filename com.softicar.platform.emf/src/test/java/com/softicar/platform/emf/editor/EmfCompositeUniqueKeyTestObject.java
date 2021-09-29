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

public class EmfCompositeUniqueKeyTestObject extends AbstractEmfObject<EmfCompositeUniqueKeyTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfCompositeUniqueKeyTestObject, EmfCompositeUniqueKeyTestObject> BUILDER = new DbObjectTableBuilder<>("Test", "UniqueKeyEntity", EmfCompositeUniqueKeyTestObject::new, EmfCompositeUniqueKeyTestObject.class);
	public static final IDbIdField<EmfCompositeUniqueKeyTestObject> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<EmfCompositeUniqueKeyTestObject> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v);
	public static final IDbIntegerField<EmfCompositeUniqueKeyTestObject> VALUE = BUILDER.addIntegerField("value", o -> o.value, (o, v) -> o.value = v).setNullable();
	public static final IDbKey<EmfCompositeUniqueKeyTestObject> UK_NAME_VALUE = BUILDER.addUniqueKey("nameValue", NAME, VALUE);
	public static final EmfObjectTable<EmfCompositeUniqueKeyTestObject, EmfTestModuleInstance> TABLE = new EmfObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private String name;
	private Integer value;

	public EmfCompositeUniqueKeyTestObject setName(String name) {

		return setValue(NAME, name);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getValue(NAME));
	}

	public EmfCompositeUniqueKeyTestObject setValue(Integer value) {

		return setValue(VALUE, value);
	}

	public Integer getValue() {

		return getValue(VALUE);
	}

	@Override
	public EmfObjectTable<EmfCompositeUniqueKeyTestObject, EmfTestModuleInstance> table() {

		return TABLE;
	}
}
