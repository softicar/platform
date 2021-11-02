package com.softicar.platform.emf.test.simple;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class EmfTestObjectGenerated extends AbstractDbObject<EmfTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestObject, EmfTestObjectGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "SimpleEntity", EmfTestObject::new, EmfTestObject.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Object"));
		BUILDER.setTitle(IDisplayString.create("Test Objects"));
	}
	public static final IDbIdField<EmfTestObject> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<EmfTestObject> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbDayField<EmfTestObject> DAY = BUILDER.addDayField("day", o -> o.day, (o, v) -> o.day = v).setNullable().setDefault(null);
	public static final IDbBooleanField<EmfTestObject> ACTIVE = BUILDER.addBooleanField("active", o -> o.active, (o, v) -> o.active = v).setDefault(true);
	public static final EmfTestObjectTable TABLE = new EmfTestObjectTable(BUILDER);
	// @formatter:on

	private Integer id;
	private String name;
	private Day day;
	private Boolean active;

	public EmfTestObject setName(String name) {

		return setValue(NAME, name);
	}

	public String getName() {

		return getValue(NAME);
	}

	public EmfTestObject setDay(Day day) {

		return setValue(DAY, day);
	}

	public Day getDay() {

		return getValue(DAY);
	}

	public EmfTestObject setActive(Boolean active) {

		return setValue(ACTIVE, active);
	}

	public Boolean isActive() {

		return getValue(ACTIVE);
	}

	@Override
	public EmfTestObjectTable table() {

		return TABLE;
	}
}
