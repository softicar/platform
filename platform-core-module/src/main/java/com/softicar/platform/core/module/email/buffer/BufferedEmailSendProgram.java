package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.retry.Retrier;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.server.AGServer;
import java.time.Duration;
import java.util.Optional;

/**
 * Sends e-mails in {@link AGBufferedEmail} and removes old entries.
 *
 * @see BufferedEmailCleaner
 * @see BufferedEmailSender
 * @author Oliver Richers
 */
@SourceCodeReferencePointUuid("0bec6bee-e588-47f7-81cc-ba4dfd9ca720")
public class BufferedEmailSendProgram implements IProgram {

	private static final Duration RETRY_DELAY_MILLIS = Duration.ofMinutes(1);

	@Override
	public void executeProgram() {

		Log.finfo("Sending emails for all servers...");
		new Retrier(this::sendForAllServers)//
			.setRetryDelay(RETRY_DELAY_MILLIS)
			.apply();
		Log.finfo("Done sending emails for all servers.");

		Log.finfo("Cleaning emails...");
		new BufferedEmailCleaner().cleanAll();
	}

	private void sendForAllServers() {

		new Sender().sendForAllServers();
	}

	private static class Sender {

		private final ExceptionsCollector collector;

		public Sender() {

			this.collector = new ExceptionsCollector();
		}

		public void sendForAllServers() {

			AGServer.TABLE//
				.createSelect()
				.where(AGServer.ACTIVE)
				.joinReverse(AGBufferedEmail.EMAIL_SERVER)
				.groupBy(AGBufferedEmail.EMAIL_SERVER)
				.where(AGBufferedEmail.ACTIVE)
				.where(AGBufferedEmail.SENT_AT.isNull())
				.forEach(this::sendEmails);

			collector.throwIfNotEmpty();
		}

		private void sendEmails(AGServer server) {

			try {
				Log.finfo("Starting email sending for email server '%s'.", server.toDisplayWithoutId());
				new BufferedEmailSender(server).sendAll();
			} catch (Exception exception) {
				collector.add(exception);
			}
		}
	}

	@Override
	public Optional<String> getDefaultCronExpression() {

		return Optional.of("* * * * *");
	}

	@Override
	public Optional<IDisplayString> getDescription() {

		return Optional
			.of(//
				CoreI18n.SENDS_SAVED_EMAILS_AND_DELETES_ALREADY_SENT_EMAILS_AFTER_ARG1_DAYS.toDisplay(BufferedEmailCleaner.MAXIMUM_DAYS_TO_KEEP));
	}
}
