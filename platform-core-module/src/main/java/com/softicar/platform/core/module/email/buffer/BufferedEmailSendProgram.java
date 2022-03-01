package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.program.IProgram;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Optional;

/**
 * Sends e-mails in {@link AGBufferedEmail} and removes old entries.
 *
 * @see BufferedEmailCleaner
 * @see BufferedEmailSender
 * @author Oliver Richers
 */
@EmfSourceCodeReferencePointUuid("0bec6bee-e588-47f7-81cc-ba4dfd9ca720")
public class BufferedEmailSendProgram implements IProgram {

	@Override
	public void executeProgram() {

		Log.finfo("Cleaning emails...");
		new BufferedEmailCleaner().cleanAll();

		Log.finfo("Sending emails...");
		new Sender().sendForAllServers();
	}

	private static class Sender {

		private final ExceptionsCollector collector;

		public Sender() {

			this.collector = new ExceptionsCollector();
		}

		public void sendForAllServers() {

			AGBufferedEmail.TABLE//
				.createSelect()
				.where(AGBufferedEmail.SENT_AT.isNull())
				.groupBy(AGBufferedEmail.EMAIL_SERVER)
				.join(AGBufferedEmail.EMAIL_SERVER)
				.where(AGServer.ACTIVE)
				.stream()
				.map(AGBufferedEmail::getEmailServer)
				.forEach(this::sendEmails);

			collector.throwExceptionIfNotEmpty();
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
}
