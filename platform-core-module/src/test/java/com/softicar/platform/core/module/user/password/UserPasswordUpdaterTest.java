package com.softicar.platform.core.module.user.password;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.security.crypt.Bcrypt;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.algorithm.AGUserPasswordAlgorithmEnum;
import java.util.List;
import org.junit.Test;

public class UserPasswordUpdaterTest extends AbstractCoreTest {

	private static final String PASSWORD = "password";
	private static final String PASSWORD2 = "password2";
	private final AGUser user;

	public UserPasswordUpdaterTest() {

		this.user = insertTestUser();
	}

	@Test
	public void testInsertionOfFirstPassword() {

		// insert password and remember time
		DayTime before = DayTime.now();
		new UserPasswordUpdater(user, PASSWORD).updatePasswordInDatabase();
		DayTime after = DayTime.now();

		// assert there is only one entry
		List<AGUserPassword> passwords = AGUserPassword.TABLE.loadAll();
		assertEquals(1, passwords.size());

		// verify all fields of the password
		AGUserPassword userPassword = passwords.get(0);
		assertSame(user, userPassword.getUser());
		assertTrue(userPassword.isActive());
		assertEquals(AGUserPasswordAlgorithmEnum.BCRYPT.getRecord(), userPassword.getAlgorithm());
		assertTrue(Bcrypt.verifyPassword(PASSWORD, userPassword.getEncryptedPassword()));
		assertTrue(userPassword.getCreatedAt().compareTo(before) >= 0);
		assertTrue(userPassword.getCreatedAt().compareTo(after) <= 0);
	}

	@Test
	public void testInsertionOfSecondPassword() {

		// insert first password
		new UserPasswordUpdater(user, PASSWORD).updatePasswordInDatabase();
		assertEquals(1, AGUserPassword.TABLE.loadAll().size());

		// insert second password
		new UserPasswordUpdater(user, PASSWORD2).updatePasswordInDatabase();
		assertEquals(2, AGUserPassword.TABLE.loadAll().size());

		// verify only the second is active
		List<AGUserPassword> activePasswords = loadActivePasswords(user);
		assertEquals(1, activePasswords.size());
		assertTrue(Bcrypt.verifyPassword(PASSWORD2, activePasswords.get(0).getEncryptedPassword()));
		assertFalse(Bcrypt.verifyPassword(PASSWORD, activePasswords.get(0).getEncryptedPassword()));
	}

	@Test
	public void testPasswordOfOtherUsersDontGetDisabled() {

		AGUser user2 = insertUser("Test", "User2");

		new UserPasswordUpdater(user, PASSWORD).updatePasswordInDatabase();
		assertEquals(1, loadActivePasswords(user).size());
		assertEquals(0, loadActivePasswords(user2).size());

		new UserPasswordUpdater(user2, PASSWORD).updatePasswordInDatabase();
		assertEquals(1, loadActivePasswords(user).size());
		assertEquals(1, loadActivePasswords(user2).size());

		new UserPasswordUpdater(user2, PASSWORD2).updatePasswordInDatabase();
		assertEquals(1, loadActivePasswords(user).size());
		assertEquals(1, loadActivePasswords(user2).size());
	}

	private static List<AGUserPassword> loadActivePasswords(AGUser user) {

		return AGUserPassword.createSelect().where(AGUserPassword.USER.equal(user)).where(AGUserPassword.ACTIVE.equal(true)).list();
	}
}
