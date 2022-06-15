package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.event.recipient.AGSystemEventEmailRecipient;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.user.AGUser;
import java.util.List;
import java.util.stream.Collectors;

public class SystemEventNotifier {

	public static void notifyAboutEvents() {

		List<AGUser> emailRecipients = AGSystemEventEmailRecipient.TABLE
			.createSelect()
			.where(AGSystemEventEmailRecipient.ACTIVE)
			.stream()
			.map(AGSystemEventEmailRecipient::getRecipient)
			.collect(Collectors.toList());
		AGCoreModuleInstance moduleInstance = AGCoreModuleInstance.getInstance();
		if (emailRecipients.isEmpty() && !moduleInstance.isTestSystem()) {
			throw new SofticarUserException(CoreI18n.NO_EMAIL_RECIPIENTS_DEFINED);
		}
		int count = AGSystemEvent.TABLE.createSelect().where(AGSystemEvent.NEEDS_CONFIRMATION).count();
		if (count > 0 && !emailRecipients.isEmpty()) {
			BufferedEmailFactory//
				.createNoReplyEmail()
				.setSubject(CoreI18n.SYSTEM_ARG1_HAS_ARG2_SYSTEM_EVENTS_THAT_NEED_CONFIRMATION.toDisplay(moduleInstance.getSystemName(), count))
				.setPlainTextContent(new PageUrlBuilder<>(SystemEventPage.class, AGCoreModuleInstance.getInstance()).build().toString())
				.addAGUsersToRecipients(emailRecipients)
				.submit();
		}
	}
}
