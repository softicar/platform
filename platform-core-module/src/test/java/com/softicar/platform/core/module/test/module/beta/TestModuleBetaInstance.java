package com.softicar.platform.core.module.test.module.beta;

import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.emf.module.IUuid;

public class TestModuleBetaInstance extends AbstractDbSubObject<TestModuleBetaInstance, AGModuleInstance>
		implements IModuleInstance<TestModuleBetaInstance> {

	//@formatter:off
	public static final DbSubObjectTableBuilder<TestModuleBetaInstance, TestModuleBetaInstance, AGModuleInstance, Integer> BUILDER = new DbSubObjectTableBuilder<>("Core", TestModuleBetaInstance.class.getSimpleName(), TestModuleBetaInstance::new, TestModuleBetaInstance.class);
	public static final IDbBaseField<TestModuleBetaInstance, AGModuleInstance, Integer> ID = BUILDER.addBaseField("id", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE);
	public static final TestModuleBetaInstanceTable TABLE = new TestModuleBetaInstanceTable(BUILDER);
	//@formatter:on

	private AGModuleInstance m_moduleInstance;

	@Override
	public TestModuleBetaInstanceTable table() {

		return TABLE;
	}

	@Override
	public IUuid getModuleUuid() {

		throw new UnsupportedOperationException();
	}
}
