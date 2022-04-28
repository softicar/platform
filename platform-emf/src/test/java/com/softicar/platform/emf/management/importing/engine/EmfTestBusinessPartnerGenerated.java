package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class EmfTestBusinessPartnerGenerated extends AbstractDbObject<EmfTestBusinessPartner> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestBusinessPartner, EmfTestBusinessPartnerGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "BusinessPartner", EmfTestBusinessPartner::new, EmfTestBusinessPartner.class);
	public static final IDbIdField<EmfTestBusinessPartner> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestBusinessPartner, EmfTestBusinessUnitModuleInstance> BUSINESS_UNIT_MODULE_INSTANCE = BUILDER.addForeignField("businessUnitModuleInstance", o->o.businessUnitModuleInstance, (o,v)->o.businessUnitModuleInstance=v, EmfTestBusinessUnitModuleInstance.ID);
	public static final IDbStringField<EmfTestBusinessPartner> VAT_ID = BUILDER.addStringField("vatId", o -> o.vatId, (o, v) -> o.vatId = v).setNullable();
	public static final IDbStringField<EmfTestBusinessPartner> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbKey<EmfTestBusinessPartner> UK_BUSINESS_UNIT_MODULE_INSTANCE_VAT_ID = BUILDER.addUniqueKey("name", BUSINESS_UNIT_MODULE_INSTANCE, VAT_ID);
	public static final EmfTestBusinessPartnerTable TABLE = new EmfTestBusinessPartnerTable(BUILDER);
	// @formatter:on

	private Integer id;
	private EmfTestBusinessUnitModuleInstance businessUnitModuleInstance;
	private String vatId;
	private String name;

	public EmfTestBusinessPartner setBusinessUnitModuleInstance(EmfTestBusinessUnitModuleInstance businessUnitModuleInstance) {

		return setValue(BUSINESS_UNIT_MODULE_INSTANCE, businessUnitModuleInstance);
	}

	public EmfTestBusinessUnitModuleInstance getBusinessUnitModuleInstance() {

		return getValue(BUSINESS_UNIT_MODULE_INSTANCE);
	}

	public EmfTestBusinessPartner setVatId(String vatId) {

		return setValue(VAT_ID, vatId);
	}

	public String getVatId() {

		return getValue(VAT_ID);
	}

	public EmfTestBusinessPartner setName(String name) {

		return setValue(NAME, name);
	}

	public String getName() {

		return getValue(NAME);
	}

	@Override
	public EmfTestBusinessPartnerTable table() {

		return TABLE;
	}
}
