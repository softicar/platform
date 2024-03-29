package com.softicar.platform.workflow.module;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.workflow.module.test.fixture.WorkflowModuleTestFixtureMethods;

public abstract class AbstractWorkflowTest extends AbstractDbTest implements WorkflowModuleTestFixtureMethods {

	protected final AGUser user;
	protected final AGWorkflowModuleInstance moduleInstance;

	public AbstractWorkflowTest() {

		this.user = insertTestUser()//
			.setEmailAddress("user@example.com")
			.save();
		this.moduleInstance = insertWorkflowModuleInstance();

		AGCoreModuleInstance.getInstance().setEmailServer(insertDummyServer()).save();
		CurrentUser.set(user);
	}
}
