package com.softicar.platform.core.module.log.notification;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.EmailFactory;
import com.softicar.platform.core.module.email.IEmail;
import com.softicar.platform.core.module.event.panic.AGModulePanicReceiver;
import com.softicar.platform.core.module.event.panic.CurrentPanicsPage;
import com.softicar.platform.core.module.log.level.AGLogLevelEnum;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.emf.module.registry.IEmfModuleRegistry;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class checks the {@link AGLogMessage} table and sends notification
 * e-mails for all messages with severity PANIC.
 * <p>
 * The panic receivers are defined by the table {@link AGModulePanicReceiver}.
 *
 * @author Marco Pilipovic
 * @author Oliver Richers
 */
@SourceCodeReferencePointUuid("011df8e1-1f16-42c2-8902-cf426403d910")
public class LogDbPanicNotificationProgram implements IProgram {

	private static final int MAX_LOG_MESSAGES = 250;

	/**
	 * This Method looks through the log_messages table for log messages of
	 * LogLevel PANIC that were not notified by now
	 */
	private Collection<AGLogMessage> getOpenPanics() {

		// load open panic messages
		List<AGLogMessage> openPanics = AGLogMessage.TABLE//
			.createSelect()
			.where(AGLogMessage.LEVEL.isEqual(AGLogLevelEnum.PANIC.getRecord()))
			.where(AGLogMessage.NOTIFICATION_TIME.isNull())
			.list(MAX_LOG_MESSAGES + 1);

		// check for too many panics
		if (openPanics.size() > MAX_LOG_MESSAGES) {
			IEmail email = EmailFactory.createNoReplyEmail();
			email.addToRecipients(AGModulePanicReceiver.getPanicReceiverEmailAddressesForModule(IEmfModuleRegistry.get().getModule(CoreModule.class)));
			email.setSubject(IDisplayString.create("CRITICAL: Too many panics."));
			email
				.setContent(
					String
						.format(
							"There are more than %s panic messages: %s",
							MAX_LOG_MESSAGES,
							new PageUrlBuilder<>(CurrentPanicsPage.class, AGCoreModuleInstance.getInstance()).build()),
					EmailContentType.HTML);
			email.submit();
			return Collections.emptyList();

		}

		return openPanics;
	}

	@Override
	public void executeProgram() {

		try {
			Collection<AGLogMessage> openPanics = getOpenPanics();
			Log.finfo("%s open panic messages", openPanics.size());
			LogDbPanicNotificationSender.send(openPanics);
		} catch (Exception exception) {
			exception.printStackTrace();
			sendSuperPanicNotification(exception);
		}
	}

	private void sendSuperPanicNotification(Exception exception) {

		IEmail email = EmailFactory.createNoReplyEmail();
		email.addToRecipients(AGModulePanicReceiver.getPanicReceiverEmailAddressesForModule(IEmfModuleRegistry.get().getModule(CoreModule.class)));
		email.setSubject(IDisplayString.create("CRITICAL: Failed to send panic notifications."));
		email
			.setContent(
				new StringBuilder()//
					.append("Hello dear\n\n")
					.append("Either the database is not available, or we have another big problem.\n")
					.append("This is very likely a critical situation.\n\n")
					.append(StackTraceFormatting.getStackTraceAsString(exception))
					.toString(),
				EmailContentType.PLAIN);
		email.submit();
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("0 0 * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional.of(CoreI18n.SENDS_PANIC_ENTRIES_BY_EMAIL);
	}
}
