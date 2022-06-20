package com.softicar.platform.demo.core.module.moment;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.demo.invoice.module.test.fixture.DemoInvoiceModuleTestFixtureMethods;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;

public class AbstractDemoCoreModuleTest extends AbstractCoreTest implements DemoInvoiceModuleTestFixtureMethods, IEmfTestEngineMethods {

	protected final AGDemoCoreModuleInstance moduleInstance;

	public AbstractDemoCoreModuleTest() {

		this.moduleInstance = insertModuleInstance(AGDemoCoreModuleInstance.TABLE);
	}
}
