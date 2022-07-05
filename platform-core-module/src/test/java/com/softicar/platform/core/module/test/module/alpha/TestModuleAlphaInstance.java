package com.softicar.platform.core.module.test.module.alpha;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.emf.module.IUuid;

public class TestModuleAlphaInstance extends AbstractDbSubObject<TestModuleAlphaInstance, AGModuleInstanceBase>
		implements IModuleInstance<TestModuleAlphaInstance> {

	//@formatter:off
	public static final DbSubObjectTableBuilder<TestModuleAlphaInstance, TestModuleAlphaInstance, AGModuleInstanceBase , Integer> BUILDER = new DbSubObjectTableBuilder<>("Core", TestModuleAlphaInstance.class.getSimpleName(), TestModuleAlphaInstance::new, TestModuleAlphaInstance.class);
	public static final IDbBaseField<TestModuleAlphaInstance, AGModuleInstanceBase, Integer> BASE = BUILDER.addBaseField("base", o->o.m_base, (o,v)->o.m_base=v, AGModuleInstanceBase.TABLE);
	public static final TestModuleAlphaInstanceTable TABLE = new TestModuleAlphaInstanceTable(BUILDER);
	//@formatter:on

	private AGModuleInstanceBase m_base;

	@Override
	public TestModuleAlphaInstanceTable table() {

		return TABLE;
	}

	@Override
	public IUuid getModuleUuid() {

		throw new UnsupportedOperationException();
	}
}
