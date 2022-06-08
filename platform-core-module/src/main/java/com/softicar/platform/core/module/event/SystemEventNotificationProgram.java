package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.event.recipient.AGSystemEventEmailRecipient;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EmfSourceCodeReferencePointUuid("19377e46-0e79-4d83-ad69-71c81520a65f")
public class SystemEventNotificationProgram implements IProgram {

	@Override
	public void executeProgram() {

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
		if (count > 0) {
			BufferedEmailFactory//
				.createNoReplyEmail()
				.setSubject(CoreI18n.SYSTEM_ARG1_HAS_ARG2_SYSTEM_EVENTS_THAT_NEED_CONFIRMATION.toDisplay(moduleInstance.getSystemName(), count))
				.setPlainTextContent(new PageUrlBuilder<>(SystemEventPage.class, new SystemModuleInstance(CoreModule.class)).build().toString())
				.addAGUsersToRecipients(emailRecipients)
				.submit();
		}
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 * * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.INFORMS_USERS_ABOUT_SYSTEM_EVENTS_THAT_NEED_CONFIRMATION);
	}
}
