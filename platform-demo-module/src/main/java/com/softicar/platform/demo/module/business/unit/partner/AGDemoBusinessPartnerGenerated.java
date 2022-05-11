package com.softicar.platform.demo.module.business.unit.partner;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;

/**
 * This is the automatically generated class AGDemoBusinessPartner for
 * database table <i>Demo.DemoBusinessPartner</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoBusinessPartnerGenerated extends AbstractDbObject<AGDemoBusinessPartner> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGDemoBusinessPartner, AGDemoBusinessPartnerGenerated> BUILDER = new DbObjectTableBuilder<>("Demo", "DemoBusinessPartner", AGDemoBusinessPartner::new, AGDemoBusinessPartner.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_BUSINESS_PARTNER);
		BUILDER.setPluralTitle(DemoI18n.DEMO_BUSINESS_PARTNERS);
	}

	public static final IDbIdField<AGDemoBusinessPartner> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbForeignField<AGDemoBusinessPartner, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(DemoI18n.TRANSACTION);
	public static final IDbForeignRowField<AGDemoBusinessPartner, AGDemoBusinessUnitModuleInstance, AGModuleInstance> MODULE_INSTANCE = BUILDER.addForeignRowField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGDemoBusinessUnitModuleInstance.MODULE_INSTANCE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbStringField<AGDemoBusinessPartner> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(DemoI18n.NAME);
	public static final IDbStringField<AGDemoBusinessPartner> VAT_ID = BUILDER.addStringField("vatId", o->o.m_vatId, (o,v)->o.m_vatId=v).setTitle(DemoI18n.VAT_ID);
	public static final IDbKey<AGDemoBusinessPartner> UK_MODULE_INSTANCE_VAT_ID = BUILDER.addUniqueKey("moduleInstanceVatId", MODULE_INSTANCE, VAT_ID);
	public static final AGDemoBusinessPartnerTable TABLE = new AGDemoBusinessPartnerTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGDemoBusinessPartner> createSelect() {

		return TABLE.createSelect();
	}

	public static AGDemoBusinessPartner get(Integer id) {

		return TABLE.get(id);
	}

	public static AGDemoBusinessPartner loadByModuleInstanceAndVatId(AGDemoBusinessUnitModuleInstance moduleInstance, String vatId) {

		return TABLE//
				.createSelect()
				.where(MODULE_INSTANCE.equal(moduleInstance))
				.where(VAT_ID.equal(vatId))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGDemoBusinessPartner setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final AGDemoBusinessUnitModuleInstance getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public final AGDemoBusinessPartner setModuleInstance(AGDemoBusinessUnitModuleInstance value) {

		return setValue(MODULE_INSTANCE, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGDemoBusinessPartner setName(String value) {

		return setValue(NAME, value);
	}

	public final String getVatId() {

		return getValue(VAT_ID);
	}

	public final AGDemoBusinessPartner setVatId(String value) {

		return setValue(VAT_ID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoBusinessPartnerTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGDemoBusinessUnitModuleInstance m_moduleInstance;
	private String m_name;
	private String m_vatId;
}

