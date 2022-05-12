package com.softicar.platform.demo.module.business.unit.test.fixtures;

import com.softicar.platform.core.module.test.instance.registry.IStandardModuleTestFixture;
import com.softicar.platform.core.module.test.instance.registry.TestFixtureRegistry;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.demo.module.test.fixture.DemoModuleTestFixture;
import com.softicar.platform.emf.table.IEmfTable;

public class DemoBusinessUnitModuleTestFixture
		implements DemoBusinessUnitModuleTestFixtureMethods, IStandardModuleTestFixture<AGDemoBusinessUnitModuleInstance> {

	private final TestFixtureRegistry registry;
	private AGDemoBusinessUnitModuleInstance moduleInstance;

	public DemoBusinessUnitModuleTestFixture(TestFixtureRegistry registry) {

		this.registry = registry;
		this.registry.registerIfMissing(DemoModuleTestFixture::new);
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

		registry.registerIfMissing(DemoModuleTestFixture::new);
		moduleInstance = insertDemoBusinessUnitModuleInstance(registry.getModuleInstance(AGDemoModuleInstance.TABLE));
		registry.getCoreModuleTestFixture().insertStandardRoleMemberships(moduleInstance);
		new DemoBusinessPartnerTestFixtures(moduleInstance).apply();
		return this;
	}
}
