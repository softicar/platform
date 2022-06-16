package com.softicar.platform.demo.person.module.test.fixtures;

import com.softicar.platform.core.module.test.instance.registry.IModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.demo.core.module.test.fixture.DemoCoreModuleTestFixture;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.emf.table.IEmfTable;

public class DemoPersonModuleTestFixture implements DemoPersonModuleTestFixtureMethods, IModuleTestFixture<AGDemoPersonModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoPersonModuleInstance moduleInstance;

	public DemoPersonModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
		this.registry.registerIfMissing(DemoCoreModuleTestFixture::new);
	}

	@Override
	public AGDemoPersonModuleInstance getInstance() {

		return moduleInstance;
	}

	@Override
	public IEmfTable<?, ?, ?> getTable() {

		return AGDemoPersonModuleInstance.TABLE;
	}

	@Override
	public IModuleTestFixture<AGDemoPersonModuleInstance> apply() {

		moduleInstance = insertDemoPersonModuleInstance(registry.getModuleInstance(AGDemoCoreModuleInstance.TABLE));
		registry.getCoreModuleTestFixture().insertStandardPermissionAssignments(moduleInstance);
		new DemoPersonsTestFixtures(moduleInstance).apply();
		return this;
	}
}
