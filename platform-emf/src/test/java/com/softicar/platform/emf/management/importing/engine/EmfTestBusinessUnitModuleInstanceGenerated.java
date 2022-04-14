package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestBusinessUnitModuleInstanceGenerated extends AbstractDbObject<EmfTestBusinessUnitModuleInstance> {

	// @formatter:off
	public static final DbObjectTableBuilder<EmfTestBusinessUnitModuleInstance, EmfTestBusinessUnitModuleInstanceGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "BusinessUnitModuleInstance", EmfTestBusinessUnitModuleInstance::new, EmfTestBusinessUnitModuleInstance.class);
//	static {
//		BUILDER.setTitle(IDisplayString.create("BusinessUnitModuleInstance"));
//		BUILDER.setTitle(IDisplayString.create("BusinessUnitModuleInstances"));
//	}
	public static final IDbIdField<EmfTestBusinessUnitModuleInstance> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestBusinessUnitModuleInstance, EmfTestObject> SCOPE_OBJECT = BUILDER.addForeignField("scopeObject", o->o.scopeObject, (o,v)->o.scopeObject=v, EmfTestObject.ID);
	public static final IDbStringField<EmfTestBusinessUnitModuleInstance> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable().setDefault(null);
	public static final IDbKey<EmfTestBusinessUnitModuleInstance> UK_NAME = BUILDER.addUniqueKey("nameDay", NAME);
	public static final EmfTestBusinessUnitModuleInstanceTable TABLE = new EmfTestBusinessUnitModuleInstanceTable(BUILDER);
	// @formatter:on

	private Integer id;
	private EmfTestObject scopeObject;
	private String name;

	public EmfTestBusinessUnitModuleInstance setName(EmfTestObject scopeObject) {

		return setValue(SCOPE_OBJECT, scopeObject);
	}

	public EmfTestObject getScopeObject() {

		return getValue(SCOPE_OBJECT);
	}

	public EmfTestBusinessUnitModuleInstance setName(String name) {

		return setValue(NAME, name);
	}

	public String getName() {

		return getValue(NAME);
	}

	@Override
	public EmfTestBusinessUnitModuleInstanceTable table() {

		return TABLE;
	}
}
