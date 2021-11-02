package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;

class AbstractUserImpersonationTest extends AbstractCoreTest {

	protected static final String EMAIL_ADDRESS = "impersonated.user@example.com";
	protected static final String RATIONALE = "Just testing.";
	protected static final LanguageEnum TEST_LANGUAGE = LanguageEnum.GERMAN;
	protected final AGUser impersonatedUser;

	public AbstractUserImpersonationTest() {

		this.impersonatedUser = insertUser("Impersonated", "User")//
			.setEmailAddress(EMAIL_ADDRESS)
			.setPreferredLanguage(TEST_LANGUAGE)
			.save();
	}
}
