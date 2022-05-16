package com.softicar.platform.demo.module.person.module.test.fixtures;

import com.softicar.platform.core.module.test.instance.registry.IStandardModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.demo.module.test.fixture.DemoModuleTestFixture;
import com.softicar.platform.emf.table.IEmfTable;

public class DemoPersonModuleTestFixture implements DemoPersonModuleTestFixtureMethods, IStandardModuleTestFixture<AGDemoPersonModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoPersonModuleInstance moduleInstance;

	public DemoPersonModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
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
	public IStandardModuleTestFixture<AGDemoPersonModuleInstance> apply() {

		registry.registerIfMissing(DemoModuleTestFixture::new);
		moduleInstance = insertDemoPersonModuleInstance(registry.getModuleInstance(AGDemoModuleInstance.TABLE));
		registry.getCoreModuleTestFixture().insertStandardRoleMemberships(moduleInstance);
		new DemoPersonTestFixtures(moduleInstance).apply();
		return this;
	}
}
