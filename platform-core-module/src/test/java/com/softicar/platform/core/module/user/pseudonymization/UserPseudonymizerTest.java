package com.softicar.platform.core.module.user.pseudonymization;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.AGUserLog;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class UserPseudonymizerTest extends AbstractDbTest {

	private static final String EMPTY = "";
	private static final String PSEUDO_FIRST_NAME = CoreI18n.PSEUDONYMIZED_USER.toEnglish();
	private static final String PSEUDO_LAST_NAME = "Mr. X";

	@Test
	public void testPseudonymize() {

		AGUser user = insertUser();

		new UserPseudonymizer(user, PSEUDO_LAST_NAME).pseudonymize();

		assertEquals(getPseudoLoginName(user), user.getLoginName());
		assertEquals(PSEUDO_FIRST_NAME, user.getFirstName());
		assertEquals(PSEUDO_LAST_NAME, user.getLastName());
		assertEquals(EMPTY, user.getEmailAddress());

		for (AGUserLog userLog: AGUserLog.TABLE.createSelect().where(AGUserLog.USER.isEqual(user))) {
			assertEquals(getPseudoLoginName(user), userLog.getLoginName());
			assertEquals(PSEUDO_FIRST_NAME, userLog.getFirstName());
			assertEquals(PSEUDO_LAST_NAME, userLog.getLastName());
			assertEquals(EMPTY, userLog.getEmailAddress());
		}
	}

	@Test(expected = SofticarUserException.class)
	public void testPseudonymizeWithActiveUser() {

		AGUser user = insertUser().setActive(true).save();

		new UserPseudonymizer(user, PSEUDO_LAST_NAME).pseudonymize();
	}

	private AGUser insertUser() {

		return new AGUser()//
			.setActive(false)
			.setLoginName("john.doe")
			.setFirstName("John")
			.setLastName("Doe")
			.setEmailAddress("john.doe@example.com")
			.setLocalization(AGCoreModuleInstance.getInstance().getDefaultLocalization())
			.save();
	}

	private String getPseudoLoginName(AGUser user) {

		return "pseudo" + user.getId();
	}
}
