package com.softicar.platform.demo.module;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.demo.module.test.fixture.DemoModuleTestFixtureMethods;

public abstract class AbstractDemoModuleTest extends AbstractDbTest implements DemoModuleTestFixtureMethods {

	protected final AGUser user;
	protected final AGDemoModuleInstance moduleInstance;

	public AbstractDemoModuleTest() {

		this.user = insertUser("Test User")//
			.setEmailAddress("user@example.com")
			.save();
		this.moduleInstance = insertDemoModuleInstance();

		CurrentUser.set(user);
	}
}
