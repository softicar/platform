package com.softicar.platform.core.module.user;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Before;
import org.junit.Test;

/**
 * References the currently active {@link AGUser}.
 * <p>
 * This class is an extension to {@link CurrentBasicUser} and is implemented in
 * terms of it, that is, it has no internal state on its own.
 *
 * @author Oliver Richers
 */
public class CurrentUserTest extends AbstractDbTest implements CoreModuleTestFixtureMethods {

	private final AGUser configuredUser;

	public CurrentUserTest() {

		configuredUser = insertTestUser();
	}

	@Before
	public void before() {

		CurrentUser.reset();
	}

	@Test
	public void returnsSystemUserByDefault() {

		CurrentUser.reset();
		assertNotNull(CurrentUser.get());
		assertSame(AGUser.getSystemUser(), CurrentUser.get());
	}

	@Test
	public void returnsPreviouslySetUser() {

		CurrentUser.set(configuredUser);
		assertNotNull(CurrentUser.get());
		assertSame(configuredUser, CurrentUser.get());
	}

	@Test
	public void returnsSofticarUserAfterReset() {

		CurrentUser.set(configuredUser);
		assertNotNull(CurrentUser.get());
		assertSame(configuredUser, CurrentUser.get());

		CurrentUser.reset();
		assertNotNull(CurrentUser.get());
		assertSame(AGUser.getSystemUser(), CurrentUser.get());
	}
}
