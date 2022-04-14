package com.softicar.platform.common.core.user;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;
import org.mockito.Mockito;

public class BasicUsersTest extends AbstractTest {

	@Test
	public void testIsSame() {

		assertTrue(BasicUsers.isSame(createUser(1), createUser(1)));
		assertFalse(BasicUsers.isSame(createUser(1), createUser(2)));
	}

	@Test
	public void testIsSameWithNull() {

		assertFalse(BasicUsers.isSame(null, null));
		assertFalse(BasicUsers.isSame(createUser(1), null));
		assertFalse(BasicUsers.isSame(null, createUser(1)));
	}

	@Test
	public void testIsSameWithNullId() {

		assertFalse(BasicUsers.isSame(createUser(null), createUser(null)));
		assertFalse(BasicUsers.isSame(createUser(1), createUser(null)));
		assertFalse(BasicUsers.isSame(createUser(null), createUser(1)));
	}

	private IBasicUser createUser(Integer id) {

		IBasicUser user = Mockito.mock(IBasicUser.class);
		Mockito.when(user.getId()).thenReturn(id);
		return user;
	}
}
