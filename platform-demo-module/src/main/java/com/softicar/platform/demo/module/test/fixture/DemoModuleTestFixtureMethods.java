package com.softicar.platform.demo.module.test.fixture;

import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.workflow.module.test.fixture.WorkflowModuleTestFixtureMethods;

public interface DemoModuleTestFixtureMethods extends WorkflowModuleTestFixtureMethods {

	default AGDemoModuleInstance insertDemoModuleInstance() {

		return insertStandardModuleInstance(AGDemoModuleInstance.TABLE);
	}
}
