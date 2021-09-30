package com.softicar.platform.emf.test.active;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.deactivation.EmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.object.AbstractEmfObject;
import com.softicar.platform.emf.test.simple.EmfTestObject;

/**
 * Used to test the (non-)detection of active-fields in
 * {@link EmfTableRowDeactivationStrategy}.
 * <p>
 * Besides the active-field, this class corresponds to {@link EmfTestObject}.
 */
public class EmfNonStandardActiveTestObject extends AbstractEmfObject<EmfNonStandardActiveTestObject> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfNonStandardActiveTestObject, EmfNonStandardActiveTestObject> BUILDER = new DbObjectTableBuilder<>("Test", "SimpleEntity", EmfNonStandardActiveTestObject::new, EmfNonStandardActiveTestObject.class);
	public static final IDbIdField<EmfNonStandardActiveTestObject> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<EmfNonStandardActiveTestObject> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbDayField<EmfNonStandardActiveTestObject> DAY = BUILDER.addDayField("day", o -> o.day, (o, v) -> o.day = v).setNullable().setDefault(null);
	public static final IDbBooleanField<EmfNonStandardActiveTestObject> NON_STANDARD_ACTIVE = BUILDER.addBooleanField("nonStandardActive", o -> o.nonStandardActive, (o, v) -> o.nonStandardActive = v).setDefault(true);
	public static final EmfNonStandardActiveTestObjectTable TABLE = new EmfNonStandardActiveTestObjectTable(BUILDER);
	// @formatter:on

	private Integer id;
	private String name;
	private Day day;
	private Boolean nonStandardActive;

	public EmfNonStandardActiveTestObject setName(String name) {

		return setValue(NAME, name);
	}

	public EmfNonStandardActiveTestObject setDay(Day day) {

		return setValue(DAY, day);
	}

	public Day getDay() {

		return getValue(DAY);
	}

	public EmfNonStandardActiveTestObject setNonStandardActive(Boolean active) {

		return setValue(NON_STANDARD_ACTIVE, active);
	}

	public Boolean isNonStandardActive() {

		return getValue(NON_STANDARD_ACTIVE);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(name);
	}

	@Override
	public EmfNonStandardActiveTestObjectTable table() {

		return TABLE;
	}
}
