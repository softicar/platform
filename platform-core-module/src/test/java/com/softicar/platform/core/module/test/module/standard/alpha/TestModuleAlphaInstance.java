package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.emf.module.IUuid;

public class TestModuleAlphaInstance extends AbstractDbSubObject<TestModuleAlphaInstance, AGModuleInstance>
		implements IModuleInstance<TestModuleAlphaInstance> {

	//@formatter:off
	public static final DbSubObjectTableBuilder<TestModuleAlphaInstance, TestModuleAlphaInstance, AGModuleInstance , Integer> BUILDER = new DbSubObjectTableBuilder<>("Core", TestModuleAlphaInstance.class.getSimpleName(), TestModuleAlphaInstance::new, TestModuleAlphaInstance.class);
	public static final IDbBaseField<TestModuleAlphaInstance, AGModuleInstance, Integer> MODULE_INSTANCE = BUILDER.addBaseField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE);
	public static final TestModuleAlphaInstanceTable TABLE = new TestModuleAlphaInstanceTable(BUILDER);
	//@formatter:on

	private AGModuleInstance m_moduleInstance;

	@Override
	public TestModuleAlphaInstanceTable table() {

		return TABLE;
	}

	@Override
	public IUuid getModuleUuid() {

		throw new UnsupportedOperationException();
	}
}
