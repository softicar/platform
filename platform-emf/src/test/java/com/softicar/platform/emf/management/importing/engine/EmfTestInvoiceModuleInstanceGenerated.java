package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestInvoiceModuleInstanceGenerated extends AbstractDbObject<EmfTestInvoiceModuleInstance> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestInvoiceModuleInstance, EmfTestInvoiceModuleInstanceGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "InvoiceModuleInstance", EmfTestInvoiceModuleInstance::new, EmfTestInvoiceModuleInstance.class);
	public static final IDbIdField<EmfTestInvoiceModuleInstance> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestInvoiceModuleInstance, EmfTestObject> MODULE_INSTANCE = BUILDER.addForeignField("moduleInstance", o->o.moduleInstance, (o,v)->o.moduleInstance=v, EmfTestObject.ID);
	public static final IDbStringField<EmfTestInvoiceModuleInstance> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v);
	public static final IDbKey<EmfTestInvoiceModuleInstance> UK_MODULE_INSTANCE_NAME = BUILDER.addUniqueKey("name", MODULE_INSTANCE, NAME);
	public static final EmfTestInvoiceModuleInstanceTable TABLE = new EmfTestInvoiceModuleInstanceTable(BUILDER);
	// @formatter:on

	private Integer id;
	private EmfTestObject moduleInstance;
	private String name;

	public EmfTestInvoiceModuleInstance setModuleInstance(EmfTestObject moduleInstance) {

		return setValue(MODULE_INSTANCE, moduleInstance);
	}

	public EmfTestObject getModuleInstance() {

		return getValue(MODULE_INSTANCE);
	}

	public EmfTestInvoiceModuleInstance setName(String name) {

		return setValue(NAME, name);
	}

	public String getName() {

		return getValue(NAME);
	}

	@Override
	public EmfTestInvoiceModuleInstanceTable table() {

		return TABLE;
	}
}
