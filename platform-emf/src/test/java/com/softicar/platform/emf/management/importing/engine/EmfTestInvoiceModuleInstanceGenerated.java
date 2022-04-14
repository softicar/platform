package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
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
	static {
		BUILDER.setTitle(IDisplayString.create("InvoiceModuleInstance"));
		BUILDER.setTitle(IDisplayString.create("InvoiceModuleInstances"));
	}
	public static final IDbIdField<EmfTestInvoiceModuleInstance> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestInvoiceModuleInstance, EmfTestObject> SCOPE_OBJECT = BUILDER.addForeignField("scopeObject", o->o.scopeObject, (o,v)->o.scopeObject=v, EmfTestObject.ID);
	public static final IDbStringField<EmfTestInvoiceModuleInstance> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbKey<EmfTestInvoiceModuleInstance> UK_NAME = BUILDER.addUniqueKey("nameDay", NAME);
	public static final EmfTestInvoiceModuleInstanceTable TABLE = new EmfTestInvoiceModuleInstanceTable(BUILDER);
	// @formatter:on

	private Integer id;
	private EmfTestObject scopeObject;
	private String name;

	public EmfTestInvoiceModuleInstance setCcopeObject(EmfTestObject scopeObject) {

		return setValue(SCOPE_OBJECT, scopeObject);
	}

	public EmfTestObject getScopeObject() {

		return getValue(SCOPE_OBJECT);
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
