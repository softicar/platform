package com.softicar.platform.core.module.event;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.page.PageUrlBuilder;

public class SystemEventNotifier {

	public static void notifyAboutEvents() {

		var moduleInstance = AGCoreModuleInstance.getInstance();
		int count = AGSystemEvent.TABLE.createSelect().where(AGSystemEvent.NEEDS_CONFIRMATION).count();
		if (count > 0) {
			BufferedEmailFactory//
				.createNoReplyEmail()
				.setSubject(CoreI18n.SYSTEM_ARG1_HAS_ARG2_SYSTEM_EVENTS_THAT_NEED_CONFIRMATION.toDisplay(moduleInstance.getSystemName(), count))
				.setPlainTextContent(new PageUrlBuilder<>(SystemEventPage.class, moduleInstance).build().toString())
				.addToRecipient(moduleInstance.getSupportEmailAddress())
				.submit();
		}
	}
}
