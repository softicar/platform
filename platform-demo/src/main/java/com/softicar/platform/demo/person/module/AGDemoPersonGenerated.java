package com.softicar.platform.demo.person.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.DemoI18n;

/**
 * This is the automatically generated class AGDemoPerson for
 * database table <i>Demo.DemoPerson</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoPersonGenerated extends AbstractDbObject<AGDemoPerson> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGDemoPerson, AGDemoPersonGenerated> BUILDER = new DbObjectTableBuilder<>("Demo", "DemoPerson", AGDemoPerson::new, AGDemoPerson.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_PERSON);
		BUILDER.setPluralTitle(DemoI18n.DEMO_PERSONS);
	}

	public static final IDbIdField<AGDemoPerson> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbForeignRowField<AGDemoPerson, AGDemoPersonModuleInstance, AGModuleInstanceBase> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGDemoPersonModuleInstance.BASE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbStringField<AGDemoPerson> FIRST_NAME = BUILDER.addStringField("firstName", o->o.m_firstName, (o,v)->o.m_firstName=v).setTitle(DemoI18n.FIRST_NAME);
	public static final IDbStringField<AGDemoPerson> LAST_NAME = BUILDER.addStringField("lastName", o->o.m_lastName, (o,v)->o.m_lastName=v).setTitle(DemoI18n.LAST_NAME);
	public static final IDbIntegerField<AGDemoPerson> IDENTITY_CARD_NUMBER = BUILDER.addIntegerField("identityCardNumber", o->o.m_identityCardNumber, (o,v)->o.m_identityCardNumber=v).setTitle(DemoI18n.IDENTITY_CARD_NUMBER);
	public static final IDbDayField<AGDemoPerson> BIRTH_DATE = BUILDER.addDayField("birthDate", o->o.m_birthDate, (o,v)->o.m_birthDate=v).setTitle(DemoI18n.BIRTH_DATE).setNullable().setDefault(null);
	public static final IDbKey<AGDemoPerson> UK_MODULE_INSTANCE_IDENTITY_CARD_NUMBER = BUILDER.addUniqueKey("moduleInstanceIdentityCardNumber", MODULE_INSTANCE, IDENTITY_CARD_NUMBER);
	public static final AGDemoPersonTable TABLE = new AGDemoPersonTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGDemoPerson> createSelect() {

		return TABLE.createSelect();
	}

	public static AGDemoPerson get(Integer id) {

		return TABLE.get(id);
	}

	public static AGDemoPerson loadByModuleInstanceAndIdentityCardNumber(AGDemoPersonModuleInstance moduleInstance, Integer identityCardNumber) {

		return TABLE//
				.createSelect()
				.where(MODULE_INSTANCE.isEqual(moduleInstance))
				.where(IDENTITY_CARD_NUMBER.isEqual(identityCardNumber))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGDemoPersonModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGDemoPerson setModuleInstance(AGDemoPersonModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final String getFirstName() {

		return getValue(FIRST_NAME);
	}

	public final AGDemoPerson setFirstName(String value) {

		return setValue(FIRST_NAME, value);
	}

	public final String getLastName() {

		return getValue(LAST_NAME);
	}

	public final AGDemoPerson setLastName(String value) {

		return setValue(LAST_NAME, value);
	}

	public final Integer getIdentityCardNumber() {

		return getValue(IDENTITY_CARD_NUMBER);
	}

	public final AGDemoPerson setIdentityCardNumber(Integer value) {

		return setValue(IDENTITY_CARD_NUMBER, value);
	}

	public final Day getBirthDate() {

		return getValue(BIRTH_DATE);
	}

	public final AGDemoPerson setBirthDate(Day value) {

		return setValue(BIRTH_DATE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoPersonTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGDemoPersonModuleInstance m_moduleInstance;
	private String m_firstName;
	private String m_lastName;
	private Integer m_identityCardNumber;
	private Day m_birthDate;
}

