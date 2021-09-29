package com.softicar.platform.core.module.test.module.standard.alpha;

import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.emf.module.IUuid;

public class TestStandardModuleAlphaInstance extends AbstractDbSubObject<TestStandardModuleAlphaInstance, AGModuleInstance>
		implements IStandardModuleInstance<TestStandardModuleAlphaInstance> {

	//@formatter:off
	public static final DbSubObjectTableBuilder<TestStandardModuleAlphaInstance, TestStandardModuleAlphaInstance, AGModuleInstance , Integer> BUILDER = new DbSubObjectTableBuilder<>("Core", TestStandardModuleAlphaInstance.class.getSimpleName(), TestStandardModuleAlphaInstance::new, TestStandardModuleAlphaInstance.class);
	public static final IDbBaseField<TestStandardModuleAlphaInstance, AGModuleInstance, Integer> MODULE_INSTANCE = BUILDER.addBaseField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE);
	public static final TestStandardModuleAlphaInstanceTable TABLE = new TestStandardModuleAlphaInstanceTable(BUILDER);
	//@formatter:on

	private AGModuleInstance m_moduleInstance;

	@Override
	public TestStandardModuleAlphaInstanceTable table() {

		return TABLE;
	}

	@Override
	public IUuid getModuleUuid() {

		throw new UnsupportedOperationException();
	}
}
