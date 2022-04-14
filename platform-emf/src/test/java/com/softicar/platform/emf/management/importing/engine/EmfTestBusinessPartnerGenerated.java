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
//	static {
//		BUILDER.setTitle(IDisplayString.create("BusinessPartner"));
//		BUILDER.setTitle(IDisplayString.create("BusinessPartners"));
//	}
	public static final IDbIdField<EmfTestBusinessPartner> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestBusinessPartner, EmfTestBusinessUnitModuleInstance> BUSINESS_UNIT_MODULE_INSTANCE = BUILDER.addForeignField("businessUnitModuleInstance", o->o.businessUnitModuleInstance, (o,v)->o.businessUnitModuleInstance=v, EmfTestBusinessUnitModuleInstance.ID);
	public static final IDbStringField<EmfTestBusinessPartner> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbKey<EmfTestBusinessPartner> UK_BUSINESS_UNIT_MODULE_INSTANCE_NAME = BUILDER.addUniqueKey("nameDay", BUSINESS_UNIT_MODULE_INSTANCE, NAME);
	public static final EmfTestBusinessPartnerTable TABLE = new EmfTestBusinessPartnerTable(BUILDER);
	// @formatter:on

	private Integer id;
	private EmfTestBusinessUnitModuleInstance businessUnitModuleInstance;
	private String name;

	public EmfTestBusinessPartner setName(EmfTestBusinessUnitModuleInstance businessUnitModuleInstance) {

		return setValue(BUSINESS_UNIT_MODULE_INSTANCE, businessUnitModuleInstance);
	}

	public EmfTestBusinessUnitModuleInstance getBusinessUnitModuleInstance() {

		return getValue(BUSINESS_UNIT_MODULE_INSTANCE);
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
