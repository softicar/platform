package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.i18n.LanguageScope;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.user.CurrentUser;
import org.junit.Test;

public class UserImpersonationNotifierTest extends AbstractUserImpersonationTest {

	@Test
	public void test() {

		new UserImpersonationNotifier(impersonatedUser, RATIONALE).notifyUser();

		AGBufferedEmail email = assertOne(AGBufferedEmail.TABLE.loadAll());
		assertEquals(EMAIL_ADDRESS, email.getTo());
		assertEquals(getExpectedSubject(), email.getSubject());
		assertContains(getExpectedPreamble(), email.getContent());
		assertContains(RATIONALE, email.getContent());
	}

	private String getExpectedSubject() {

		try (LanguageScope scope = new LanguageScope(TEST_LANGUAGE)) {
			return CoreI18n.USER_IMPERSONATION.toString();
		}
	}

	private String getExpectedPreamble() {

		try (LanguageScope scope = new LanguageScope(TEST_LANGUAGE)) {
			return CoreI18n.ARG1_STARTED_A_USER_IMPERSONATION_SESSION_OF_YOUR_USER_ARG2//
				.toDisplay(CurrentUser.get().toDisplay(), impersonatedUser.toDisplay())
				.toString();
		}
	}
}
