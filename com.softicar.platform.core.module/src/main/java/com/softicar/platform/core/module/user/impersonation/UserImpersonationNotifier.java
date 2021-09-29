package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.LanguageScope;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.EmailFactory;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;

/**
 * Informs a user that another user impersonated them.
 *
 * @author Oliver Richers
 */
class UserImpersonationNotifier {

	private final AGUser impersonatedUser;
	private final String rationale;

	public UserImpersonationNotifier(AGUser impersonatedUser, String rationale) {

		this.impersonatedUser = impersonatedUser;
		this.rationale = rationale;
	}

	public void notifyUser() {

		EmailFactory//
			.createNoReplyEmail()
			.addToRecipient(impersonatedUser)
			.setSubject(IDisplayString.create(getNotificationSubject()))
			.setPlainTextContent(getNotificationText())
			.submit();
	}

	public String getNotificationSubject() {

		try (LanguageScope scope = new LanguageScope(impersonatedUser.getLanguageEnum())) {
			return CoreI18n.USER_IMPERSONATION.toString();
		}
	}

	public String getNotificationText() {

		try (LanguageScope scope = new LanguageScope(impersonatedUser.getLanguageEnum())) {
			return CoreI18n.ARG1_STARTED_A_USER_IMPERSONATION_SESSION_OF_YOUR_USER_ARG2//
				.toDisplay(CurrentUser.get().toDisplay(), impersonatedUser.toDisplay())
				.concat("\n\n")
				.concatSentence(CoreI18n.THE_FOLLOWING_RATIONALE_WAS_GIVEN)
				.concat("\n\n")
				.concat(rationale)
				.toString();
		}
	}
}
