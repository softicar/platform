package com.softicar.platform.demo.module.core.module.test.fixture;

import com.softicar.platform.demo.module.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.workflow.module.test.fixture.WorkflowModuleTestFixtureMethods;

public interface DemoCoreModuleTestFixtureMethods extends WorkflowModuleTestFixtureMethods {

	default AGDemoCoreModuleInstance insertDemoModuleInstance() {

		return insertStandardModuleInstance(AGDemoCoreModuleInstance.TABLE);
	}
}
