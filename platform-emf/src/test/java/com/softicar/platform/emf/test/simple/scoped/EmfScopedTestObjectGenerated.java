package com.softicar.platform.emf.test.simple.scoped;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfScopedTestObjectGenerated extends AbstractDbObject<EmfScopedTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfScopedTestObject, EmfScopedTestObjectGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "SimpleScopedEntity", EmfScopedTestObject::new, EmfScopedTestObject.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Scoped Test Object"));
		BUILDER.setTitle(IDisplayString.create("Scoped Test Objects"));
	}
	public static final IDbIdField<EmfScopedTestObject> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfScopedTestObject, EmfTestObject> SCOPE = BUILDER.addForeignField("scope", o->o.scope, (o,v)->o.scope=v, EmfTestObject.ID);
	public static final IDbDayField<EmfScopedTestObject> DAY = BUILDER.addDayField("day", o -> o.day, (o, v) -> o.day = v).setNullable().setDefault(null);
	public static final IDbStringField<EmfScopedTestObject> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v);
	public static final IDbBooleanField<EmfScopedTestObject> ACTIVE = BUILDER.addBooleanField("active", o -> o.active, (o, v) -> o.active = v).setDefault(true);
	public static final EmfScopedTestObjectTable TABLE = new EmfScopedTestObjectTable(BUILDER);
	public static final IDbKey<EmfScopedTestObject> UK_NAME_DAY = BUILDER.addUniqueKey("nameScope", NAME, SCOPE);
	public static final IDbKey<EmfScopedTestObject> UK_ID_NAME = BUILDER.addUniqueKey("idName", ID, NAME);
	public static final IDbKey<EmfScopedTestObject> KEY_DAY_ACTIVE = BUILDER.addIndexKey("dayActive", SCOPE, ACTIVE);
	// @formatter:on

	private Integer id;
	private EmfTestObject scope;
	private Day day;
	private String name;
	private Boolean active;

	public EmfScopedTestObject setScope(EmfTestObject scope) {

		return setValue(SCOPE, scope);
	}

	public EmfTestObject getScope() {

		return getValue(SCOPE);
	}

	public EmfScopedTestObject setDay(Day day) {

		return setValue(DAY, day);
	}

	public Day getDay() {

		return getValue(DAY);
	}

	public EmfScopedTestObject setName(String name) {

		return setValue(NAME, name);
	}

	public String getName() {

		return getValue(NAME);
	}

	public EmfScopedTestObject setActive(Boolean active) {

		return setValue(ACTIVE, active);
	}

	public Boolean isActive() {

		return getValue(ACTIVE);
	}

	@Override
	public EmfScopedTestObjectTable table() {

		return TABLE;
	}
}
