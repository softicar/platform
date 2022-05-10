package com.softicar.platform.demo.module.test.fixture;

import com.softicar.platform.core.module.test.instance.registry.IStandardModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.emf.table.IEmfTable;

public class DemoModuleTestFixture implements DemoModuleTestFixtureMethods, IStandardModuleTestFixture<AGDemoInvoiceModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoInvoiceModuleInstance moduleInstance;

	public DemoModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
	}

	@Override
	public AGDemoInvoiceModuleInstance getInstance() {

		return moduleInstance;
	}

	@Override
	public IEmfTable<?, ?, ?> getTable() {

		return AGDemoInvoiceModuleInstance.TABLE;
	}

	@Override
	public IStandardModuleTestFixture<AGDemoInvoiceModuleInstance> apply() {

		this.moduleInstance = insertDemoModuleInstance();
		registry.getCoreModuleTestFixture().insertStandardRoleMemberships(moduleInstance);
		new DemoInvoicesTestFixture(moduleInstance).apply();
		return this;
	}
}
