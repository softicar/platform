package com.softicar.platform.demo.module.business.unit.partner.contact;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.demo.module.business.unit.partner.AGDemoBusinessPartner;

/**
 * This is the automatically generated class AGDemoBusinessPartnerContact for
 * database table <i>Demo.DemoBusinessPartnerContact</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoBusinessPartnerContactGenerated extends AbstractDbObject<AGDemoBusinessPartnerContact> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGDemoBusinessPartnerContact, AGDemoBusinessPartnerContactGenerated> BUILDER = new DbObjectTableBuilder<>("Demo", "DemoBusinessPartnerContact", AGDemoBusinessPartnerContact::new, AGDemoBusinessPartnerContact.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_BUSINESS_PARTNER_CONTACT);
		BUILDER.setPluralTitle(DemoI18n.DEMO_BUSINESS_PARTNER_CONTACTS);
	}

	public static final IDbIdField<AGDemoBusinessPartnerContact> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbForeignField<AGDemoBusinessPartnerContact, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(DemoI18n.TRANSACTION);
	public static final IDbForeignField<AGDemoBusinessPartnerContact, AGDemoBusinessPartner> BUSINESS_PARTNER = BUILDER.addForeignField("businessPartner", o->o.m_businessPartner, (o,v)->o.m_businessPartner=v, AGDemoBusinessPartner.ID).setTitle(DemoI18n.BUSINESS_PARTNER);
	public static final IDbStringField<AGDemoBusinessPartnerContact> FIRST_NAME = BUILDER.addStringField("firstName", o->o.m_firstName, (o,v)->o.m_firstName=v).setTitle(DemoI18n.FIRST_NAME);
	public static final IDbStringField<AGDemoBusinessPartnerContact> LAST_NAME = BUILDER.addStringField("lastName", o->o.m_lastName, (o,v)->o.m_lastName=v).setTitle(DemoI18n.LAST_NAME);
	public static final IDbIntegerField<AGDemoBusinessPartnerContact> EMPLOYEE_ID = BUILDER.addIntegerField("employeeId", o->o.m_employeeId, (o,v)->o.m_employeeId=v).setTitle(DemoI18n.EMPLOYEE_ID);
	public static final IDbKey<AGDemoBusinessPartnerContact> UK_BUSINESS_PARTNER_EMPLOYEE_ID = BUILDER.addUniqueKey("businessPartnerEmployeeId", BUSINESS_PARTNER, EMPLOYEE_ID);
	public static final AGDemoBusinessPartnerContactTable TABLE = new AGDemoBusinessPartnerContactTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGDemoBusinessPartnerContact> createSelect() {

		return TABLE.createSelect();
	}

	public static AGDemoBusinessPartnerContact get(Integer id) {

		return TABLE.get(id);
	}

	public static AGDemoBusinessPartnerContact loadByBusinessPartnerAndEmployeeId(AGDemoBusinessPartner businessPartner, Integer employeeId) {

		return TABLE//
				.createSelect()
				.where(BUSINESS_PARTNER.equal(businessPartner))
				.where(EMPLOYEE_ID.equal(employeeId))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGDemoBusinessPartnerContact setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Integer getBusinessPartnerID() {

		return getValueId(BUSINESS_PARTNER);
	}

	public final AGDemoBusinessPartner getBusinessPartner() {

		return getValue(BUSINESS_PARTNER);
	}

	public final AGDemoBusinessPartnerContact setBusinessPartner(AGDemoBusinessPartner value) {

		return setValue(BUSINESS_PARTNER, value);
	}

	public final String getFirstName() {

		return getValue(FIRST_NAME);
	}

	public final AGDemoBusinessPartnerContact setFirstName(String value) {

		return setValue(FIRST_NAME, value);
	}

	public final String getLastName() {

		return getValue(LAST_NAME);
	}

	public final AGDemoBusinessPartnerContact setLastName(String value) {

		return setValue(LAST_NAME, value);
	}

	public final Integer getEmployeeId() {

		return getValue(EMPLOYEE_ID);
	}

	public final AGDemoBusinessPartnerContact setEmployeeId(Integer value) {

		return setValue(EMPLOYEE_ID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoBusinessPartnerContactTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGDemoBusinessPartner m_businessPartner;
	private String m_firstName;
	private String m_lastName;
	private Integer m_employeeId;
}

