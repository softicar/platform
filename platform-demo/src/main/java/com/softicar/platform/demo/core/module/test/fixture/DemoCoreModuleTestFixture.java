package com.softicar.platform.demo.core.module.test.fixture;

import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixture;
import com.softicar.platform.core.module.test.fixture.ITestFixture;
import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;

public final class DemoCoreModuleTestFixture implements DemoCoreModuleTestFixtureMethods, ITestFixture {

	private AGDemoCoreModuleInstance moduleInstance;

	public AGDemoCoreModuleInstance getModuleInstance() {

		return moduleInstance;
	}

	@Override
	public void apply() {

		this.moduleInstance = insertDemoModuleInstance();
		use(CoreModuleTestFixture.class).insertStandardPermissionAssignments(moduleInstance);
	}
}
