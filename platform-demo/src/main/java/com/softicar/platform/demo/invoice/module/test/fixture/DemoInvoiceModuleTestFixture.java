package com.softicar.platform.demo.invoice.module.test.fixture;

import com.softicar.platform.core.module.test.instance.registry.IStandardModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.demo.person.module.test.fixtures.DemoPersonModuleTestFixture;
import com.softicar.platform.emf.table.IEmfTable;

public class DemoInvoiceModuleTestFixture implements DemoInvoiceModuleTestFixtureMethods, IStandardModuleTestFixture<AGDemoInvoiceModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoInvoiceModuleInstance moduleInstance;

	public DemoInvoiceModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
		this.registry.registerIfMissing(DemoPersonModuleTestFixture::new);
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

		this.moduleInstance = insertDemoInvoiceModuleInstance(registry.getModuleInstance(AGDemoPersonModuleInstance.TABLE));
		registry.getCoreModuleTestFixture().insertStandardRoleMemberships(moduleInstance);
		new DemoInvoicesTestFixture(moduleInstance).apply();
		return this;
	}
}
