package com.softicar.platform.demo.module.business.unit.test.fixtures;

import com.softicar.platform.core.module.test.instance.registry.IStandardModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.emf.table.IEmfTable;

public class DemoBusinessUnitModuleTestFixture
		implements DemoBusinessUnitModuleTestFixtureMethods, IStandardModuleTestFixture<AGDemoBusinessUnitModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoBusinessUnitModuleInstance moduleInstance;

	public DemoBusinessUnitModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
	}

	@Override
	public AGDemoBusinessUnitModuleInstance getInstance() {

		return moduleInstance;
	}

	@Override
	public IEmfTable<?, ?, ?> getTable() {

		return AGDemoBusinessUnitModuleInstance.TABLE;
	}

	@Override
	public IStandardModuleTestFixture<AGDemoBusinessUnitModuleInstance> apply() {

		this.moduleInstance = insertDemoBusinessUnitModuleInstance();
		registry.getCoreModuleTestFixture().insertStandardRoleMemberships(moduleInstance);
//		new DemoInvoicesTestFixture(moduleInstance).apply();
		return this;
	}
}
