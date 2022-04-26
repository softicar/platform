package com.softicar.platform.core.module.test;

import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import org.junit.Rule;

public abstract class AbstractCoreTest extends AbstractDbTest implements IDomTestExecutionEngineMethods, CoreModuleTestFixtureMethods {

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	public AbstractCoreTest() {

		AGUser user = insertUserWithoutLogging();
		CurrentUser.set(user);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	// Bootstrapping Problem
	// ---------------------
	// We need to insert the user without triggering the logging,
	// since the logging depends on the current user.
	private AGUser insertUserWithoutLogging() {

		int userId = AGUser.TABLE//
			.createInsert()
			.set(AGUser.LOGIN_NAME, "current.user")
			.set(AGUser.FIRST_NAME, "Current")
			.set(AGUser.LAST_NAME, "User")
			.set(AGUser.LOCALIZATION, AGCoreModuleInstance.getInstance().getDefaultLocalization())
			.execute();
		return AGUser.get(userId);
	}
}
