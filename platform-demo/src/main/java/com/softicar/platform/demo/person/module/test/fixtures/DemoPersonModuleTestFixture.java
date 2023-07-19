package com.softicar.platform.demo.person.module.test.fixtures;

import com.softicar.platform.core.module.test.fixture.ITestFixture;
import com.softicar.platform.demo.core.module.test.fixture.DemoCoreModuleTestFixture;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;

public final class DemoPersonModuleTestFixture implements DemoPersonModuleTestFixtureMethods, ITestFixture {

	private AGDemoPersonModuleInstance moduleInstance;

	public AGDemoPersonModuleInstance getModuleInstance() {

		return moduleInstance;
	}

	@Override
	public void apply() {

		moduleInstance = insertDemoPersonModuleInstance(use(DemoCoreModuleTestFixture.class).getModuleInstance());
		insertStandardPermissionAssignments(moduleInstance);
		new DemoPersonsTestFixture(moduleInstance).apply();
	}
}
