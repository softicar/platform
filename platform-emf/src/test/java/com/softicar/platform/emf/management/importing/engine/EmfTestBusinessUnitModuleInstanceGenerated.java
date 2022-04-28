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
	public static final IDbIdField<EmfTestBusinessUnitModuleInstance> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<EmfTestBusinessUnitModuleInstance, EmfTestObject> MODULE_INSTANCE = BUILDER.addForeignField("moduleInstance", o->o.moduleInstance, (o,v)->o.moduleInstance=v, EmfTestObject.ID);
	public static final IDbStringField<EmfTestBusinessUnitModuleInstance> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v).setNullable();
	public static final IDbKey<EmfTestBusinessUnitModuleInstance> UK_MODULE_INSTANCE_NAME = BUILDER.addUniqueKey("name", MODULE_INSTANCE, NAME);
	public static final EmfTestBusinessUnitModuleInstanceTable TABLE = new EmfTestBusinessUnitModuleInstanceTable(BUILDER);
	// @formatter:on

	private Integer id;
	private EmfTestObject moduleInstance;
	private String name;

	public EmfTestBusinessUnitModuleInstance setModuleInstance(EmfTestObject moduleInstance) {

		return setValue(MODULE_INSTANCE, moduleInstance);
	}

	public EmfTestObject getModuleInstance() {

		return getValue(MODULE_INSTANCE);
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
