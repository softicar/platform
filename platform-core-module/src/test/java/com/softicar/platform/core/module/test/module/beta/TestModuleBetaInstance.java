package com.softicar.platform.core.module.test.module.beta;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.emf.module.IUuid;

public class TestModuleBetaInstance extends AbstractDbSubObject<TestModuleBetaInstance, AGModuleInstanceBase>
		implements IModuleInstance<TestModuleBetaInstance> {

	//@formatter:off
	public static final DbSubObjectTableBuilder<TestModuleBetaInstance, TestModuleBetaInstance, AGModuleInstanceBase, Integer> BUILDER = new DbSubObjectTableBuilder<>("Core", TestModuleBetaInstance.class.getSimpleName(), TestModuleBetaInstance::new, TestModuleBetaInstance.class);
	public static final IDbBaseField<TestModuleBetaInstance, AGModuleInstanceBase, Integer> BASE = BUILDER.addBaseField("base", o->o.m_base, (o,v)->o.m_base=v, AGModuleInstanceBase.TABLE);
	public static final TestModuleBetaInstanceTable TABLE = new TestModuleBetaInstanceTable(BUILDER);
	//@formatter:on

	private AGModuleInstanceBase m_base;

	@Override
	public TestModuleBetaInstanceTable table() {

		return TABLE;
	}

	@Override
	public IUuid getModuleUuid() {

		throw new UnsupportedOperationException();
	}
}
