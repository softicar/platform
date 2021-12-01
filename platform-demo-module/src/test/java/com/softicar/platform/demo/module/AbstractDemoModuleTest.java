package com.softicar.platform.demo.module;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.demo.module.test.fixture.DemoModuleTestFixtureMethods;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;

public abstract class AbstractDemoModuleTest extends AbstractCoreTest implements DemoModuleTestFixtureMethods, IEmfTestEngineMethods {

	protected final AGDemoModuleInstance moduleInstance;

	public AbstractDemoModuleTest() {

		this.moduleInstance = insertDemoModuleInstance();
	}
}
