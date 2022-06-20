package com.softicar.platform.demo.core.module.test.fixture;

import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.workflow.module.test.fixture.WorkflowModuleTestFixtureMethods;

public interface DemoCoreModuleTestFixtureMethods extends WorkflowModuleTestFixtureMethods {

	default AGDemoCoreModuleInstance insertDemoModuleInstance() {

		return insertModuleInstance(AGDemoCoreModuleInstance.TABLE);
	}
}
