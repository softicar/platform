package com.softicar.platform.demo.module.moment;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.invoice.module.test.fixture.DemoInvoiceModuleTestFixtureMethods;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;

public class AbstractDemoModuleTest extends AbstractCoreTest implements DemoInvoiceModuleTestFixtureMethods, IEmfTestEngineMethods {

	protected final AGDemoModuleInstance moduleInstance;

	public AbstractDemoModuleTest() {

		this.moduleInstance = insertStandardModuleInstance(AGDemoModuleInstance.TABLE);
	}
}
