package com.softicar.platform.core.module.log.notification;

import com.softicar.platform.common.container.map.list.IListMap;
import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.network.url.Url;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.EmailFactory;
import com.softicar.platform.core.module.email.IEmail;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.core.module.module.overview.ModuleOverviewPage;
import com.softicar.platform.core.module.page.PageUrlBuilder;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.core.transaction.DbTransaction;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

class LogDbPanicNotificationSender {

	private static final String SEPARATOR = "\r\n\r\n--------------------\r\n\r\n";

	public static void send(Collection<AGLogMessage> messages) {

		if (messages.isEmpty()) {
			return;
		}

		AGLogMessage.PROCESS.prefetch(messages);

		// aggregate messages by process
		IListMap<String, AGLogMessage> messageMap = new ListTreeMap<>();
		for (AGLogMessage message: messages) {
			messageMap.addToList(message.getProcess().getClassName(), message);
		}

		for (Entry<String, List<AGLogMessage>> entry: messageMap.entrySet()) {
			// create notification text
			String notification = entry//
				.getValue()
				.stream()
				.map(message -> new LogDbPanicNotificationCreator(message).create())
				.collect(Collectors.joining(SEPARATOR));

			// get notification receivers
			Collection<String> receivers = new LogDbPanicNotificationReceiverLoader(entry.getKey()).load();
			if (receivers.isEmpty()) {
				notification = new StringBuilder()//
					.append("Please define at least one panic receiver:\r\n")
					.append(getModuleOverviewPageUrl().toString())
					.append("\r\n\r\n")
					.append(notification)
					.toString();
				receivers = getDefaultRecipients();
			}

			// send notification
			try (DbTransaction transaction = new DbTransaction()) {
				Log.finfo("sending notification for %s panics in %s", entry.getValue().size(), entry.getKey());
				send("Panic in " + entry.getKey(), notification, receivers);
				for (AGLogMessage message: entry.getValue()) {
					setNotificationTime(message);
				}
				transaction.commit();
			}
		}
	}

	private static Url getModuleOverviewPageUrl() {

		return new PageUrlBuilder<>(//
			ModuleOverviewPage.class,
			AGCoreModuleInstance.getInstance()).build();
	}

	private static void send(String subject, String message, Collection<String> receivers) {

		// send message
		IEmail email = EmailFactory.createNoReplyEmail();
		email.addToRecipients(receivers);
		email.setSubject(IDisplayString.create(subject));
		email.setContent(message, EmailContentType.PLAIN);
		email.submit();
	}

	private static void setNotificationTime(AGLogMessage message) {

		message.setNotificationTime(DayTime.now());
		message.save();
	}

	private static Set<String> getDefaultRecipients() {

		Set<String> defaultRecipients = new TreeSet<>();
		defaultRecipients.add(AGUser.getSystemUser().getEmailAddress());
		return defaultRecipients;
	}
}
