package com.softicar.platform.demo.invoice.module.test.fixture;

import com.softicar.platform.core.module.test.fixture.ITestFixture;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.person.module.test.fixtures.DemoPersonModuleTestFixture;

public final class DemoInvoiceModuleTestFixture implements DemoInvoiceModuleTestFixtureMethods, ITestFixture {

	private AGDemoInvoiceModuleInstance moduleInstance;

	public AGDemoInvoiceModuleInstance getModuleInstance() {

		return moduleInstance;
	}

	@Override
	public void apply() {

		this.moduleInstance = insertDemoInvoiceModuleInstance(use(DemoPersonModuleTestFixture.class).getModuleInstance());
		insertStandardPermissionAssignments(moduleInstance);
		new DemoInvoicesTestFixture(moduleInstance).apply();
	}
}
