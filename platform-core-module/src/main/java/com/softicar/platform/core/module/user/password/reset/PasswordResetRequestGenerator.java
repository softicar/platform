package com.softicar.platform.core.module.user.password.reset;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.common.network.url.Url;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.IEmail;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.page.service.PageServiceFactory;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.dom.elements.dialog.DomModalAlertDialog;
import java.util.UUID;

public class PasswordResetRequestGenerator {

	private final AGUser user;

	public PasswordResetRequestGenerator(AGUser user) {

		this.user = user;
	}

	public void createResetRequest() {

		if (AGUserPasswordResetRequest.isResetLimitReachedForUser(user)) {
			throw new SofticarUserException(CoreI18n.TOO_MANY_PASSWORD_RESET_REQUESTS);
		} else {
			AGCoreModuleInstance.getInstance().getEmailServerOrThrow();
			try (DbTransaction transaction = new DbTransaction()) {
				var request = new AGUserPasswordResetRequest()//
					.setUser(user)
					.setUuid(UUID.randomUUID().toString())
					.save();
				sendPaswordResetRequestNotification(request);
				transaction.commit();
			}
			new DomModalAlertDialog(
				CoreI18n.PASSWORD_RESET_REQUEST_HAS_BEEN_CREATED//
					.concat("\n\n")
					.concat(CoreI18n.AN_EMAIL_WAS_SENT_TO_YOUR_ACCOUNT)
					.concatSentence(CoreI18n.PLEASE_CHECK_YOUR_INBOX)).open();
		}
	}

	private void sendPaswordResetRequestNotification(AGUserPasswordResetRequest request) {

		try (var scope = new LocaleScope(user.getLocale())) {
			StringBuilder sb = new StringBuilder();
			sb.append(CoreI18n.A_PASSWORD_RESET_REQUEST_HAS_BEEN_CREATED_FOR_YOUR_ACCOUNT);
			sb.append("\r\n");
			sb.append(CoreI18n.PLEASE_CLICK_THIS_LINK_TO_RESET_YOUR_PASSWORD);
			sb.append("\r\n");
			sb.append(getUrl(request).toString());
			sb.append("\r\n");
			sb.append(CoreI18n.IF_YOU_DID_NOT_REQUEST_THIS_YOU_CAN_IGNORE_THIS_EMAIL);
			sb.append("\r\n");

			IEmail email = BufferedEmailFactory.createNoReplyEmail();
			email.addToRecipient(user.getEmailAddress());
			email.setSubject(CoreI18n.USER_PASSWORD_RESET_REQUESTED);
			email.setContent(sb.toString(), EmailContentType.PLAIN);
			email.submit();
		}
	}

	private Url getUrl(AGUserPasswordResetRequest request) {

		return new WebServiceUrlBuilder(PageServiceFactory.class)//
			.addParameter(PasswordResetRequestParameterParser.getPasswordResetParameter(), request.getUuid())
			.build();
	}
}
