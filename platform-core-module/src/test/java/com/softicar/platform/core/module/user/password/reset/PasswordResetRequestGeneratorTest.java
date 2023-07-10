package com.softicar.platform.core.module.user.password.reset;

import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.clock.TestClock;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import java.time.Instant;
import org.junit.Test;

public class PasswordResetRequestGeneratorTest extends AbstractCoreTest {

	private final AGUser user;
	private final PasswordResetRequestGenerator generator;

	public PasswordResetRequestGeneratorTest() {

		this.user = CurrentUser.get();
		this.generator = new PasswordResetRequestGenerator(user);
		AGCoreModuleInstance.getInstance().setEmailServer(insertDummyServer()).save();
	}

	@Test
	public void testCreateRequestForUser() {

		generator.createResetRequest();
		assertCreatedRequests(1);
		assertRequestLimitNotReached();
	}

	@Test
	public void testRequestLimit() {

		//Create requests while under limit
		generator.createResetRequest();
		generator.createResetRequest();
		assertCreatedRequests(2);
		assertRequestLimitNotReached();

		generator.createResetRequest();
		assertCreatedRequests(3);
		assertRequestLimitReached();

		//Try to create more requests
		generator.createResetRequest();
		assertCreatedRequests(3);
	}

	@Test
	public void testRequestLimitAfterWaiting() {

		//Create requests while under limit
		generator.createResetRequest();
		generator.createResetRequest();
		assertCreatedRequests(2);
		assertRequestLimitNotReached();

		//Create requests to reach limit
		generator.createResetRequest();
		assertCreatedRequests(3);
		assertRequestLimitReached();

		//Wait 24h so the limit gets reset
		CurrentClock.set(new TestClock(Instant.now().plusSeconds(86400)));

		assertRequestLimitNotReached();
		generator.createResetRequest();
		assertCreatedRequests(4);
	}

	private void assertCreatedRequests(int count) {

		var requests = AGUserPasswordResetRequest.TABLE//
			.createSelect()
			.where(AGUserPasswordResetRequest.USER.isEqual(user))
			.count();
		assertEquals(count, requests);
	}

	private void assertRequestLimitNotReached() {

		assertFalse(AGUserPasswordResetRequest.isResetLimitReachedForUser(user));
	}

	private void assertRequestLimitReached() {

		assertTrue(AGUserPasswordResetRequest.isResetLimitReachedForUser(user));
	}
}
