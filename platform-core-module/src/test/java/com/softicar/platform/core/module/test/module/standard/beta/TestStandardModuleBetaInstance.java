package com.softicar.platform.core.module.test.module.standard.beta;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.emf.module.IUuid;

public class TestStandardModuleBetaInstance extends AbstractDbSubObject<TestStandardModuleBetaInstance, AGModuleInstance>
		implements IStandardModuleInstance<TestStandardModuleBetaInstance> {

	//@formatter:off
	public static final DbSubObjectTableBuilder<TestStandardModuleBetaInstance, TestStandardModuleBetaInstance, AGModuleInstance, Integer> BUILDER = new DbSubObjectTableBuilder<>("Core", TestStandardModuleBetaInstance.class.getSimpleName(), TestStandardModuleBetaInstance::new, TestStandardModuleBetaInstance.class);
	public static final IDbBaseField<TestStandardModuleBetaInstance, AGModuleInstance, Integer> ID = BUILDER.addBaseField("id", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE);
	public static final TestStandardModuleBetaInstanceTable TABLE = new TestStandardModuleBetaInstanceTable(BUILDER);
	//@formatter:on

	private AGModuleInstance m_moduleInstance;

	@Override
	public TestStandardModuleBetaInstanceTable table() {

		return TABLE;
	}

	@Override
	public IUuid getModuleUuid() {

		throw new UnsupportedOperationException();
	}
}
