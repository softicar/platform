package com.softicar.platform.demo.core.module.test.fixture;

import com.softicar.platform.core.module.test.instance.registry.IModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.emf.table.IEmfTable;

public class DemoCoreModuleTestFixture implements DemoCoreModuleTestFixtureMethods, IModuleTestFixture<AGDemoCoreModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoCoreModuleInstance moduleInstance;

	public DemoCoreModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
	}

	@Override
	public AGDemoCoreModuleInstance getInstance() {

		return moduleInstance;
	}

	@Override
	public IEmfTable<?, ?, ?> getTable() {

		return AGDemoCoreModuleInstance.TABLE;
	}

	@Override
	public IModuleTestFixture<AGDemoCoreModuleInstance> apply() {

		this.moduleInstance = insertDemoModuleInstance();
		registry.getCoreModuleTestFixture().insertStandardPermissionAssignments(moduleInstance);
		return this;
	}
}
