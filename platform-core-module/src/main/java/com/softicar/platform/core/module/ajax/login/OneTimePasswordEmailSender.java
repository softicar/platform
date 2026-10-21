package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Objects;

/**
 * Sends a {@link OneTimePassword} to an {@link AGUser} via email.
 *
 * @author Oliver Richers
 * @author Semsudin Mekanic
 */
class OneTimePasswordEmailSender {

	private final AGUser user;

	public OneTimePasswordEmailSender(AGUser user) {

		this.user = Objects.requireNonNull(user);
	}

	public void send(OneTimePassword password) {

		try (var scope = new LocaleScope(user.getLocale())) {
			BufferedEmailFactory//
				.createNoReplyEmail()
				.setSubject(CoreI18n.ONE_TIME_PASSWORD)
				.addToRecipient(user)
				.setContent(//
					new DisplayString()
						.append(
							CoreI18n.ONE_TIME_PASSWORD_ARG1_TO_LOG_IN_TO_YOUR_ARG2_ACCOUNT_ARG3
								.toDisplay("#" + password.getIndex(), AGCoreModuleInstance.getSystemIdentifier(), user.getLoginName()))
						.append("\r\n\r\n")
						.append(password.getText())
						.toString(),
					EmailContentType.PLAIN)
				.submit();
		}
	}
}
