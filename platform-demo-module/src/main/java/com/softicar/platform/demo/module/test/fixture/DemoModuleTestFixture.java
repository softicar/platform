package com.softicar.platform.demo.module.test.fixture;

import com.softicar.platform.core.module.test.instance.registry.IStandardModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.emf.table.IEmfTable;

public class DemoModuleTestFixture implements DemoModuleTestFixtureMethods, IStandardModuleTestFixture<AGDemoModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoModuleInstance moduleInstance;

	public DemoModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
	}

	@Override
	public AGDemoModuleInstance getInstance() {

		return moduleInstance;
	}

	@Override
	public IEmfTable<?, ?, ?> getTable() {

		return AGDemoModuleInstance.TABLE;
	}

	@Override
	public IStandardModuleTestFixture<AGDemoModuleInstance> apply() {

		this.moduleInstance = insertDemoModuleInstance();
		registry.getCoreModuleTestFixture().insertStandardRoleMemberships(moduleInstance);
		new DemoInvoicesTestFixture(moduleInstance).apply();
		return this;
	}
}
