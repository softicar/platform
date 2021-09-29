package com.softicar.platform.core.module.email;

import com.softicar.platform.core.module.email.buffer.BufferedEmail;
import com.softicar.platform.core.module.email.transport.EmailTransport;
import com.softicar.platform.core.module.email.transport.IEmailTransportServer;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import java.util.Properties;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

public class EmailFactory {

	public static IEmail createNoReplyEmail() {

		return new BufferedEmail()//
			.setFrom(AGCoreModuleInstance.getInstance().getNoReplyEmailAddress());
	}

	public static IEmail createEmail(String fromAddressString) {

		return createEmail(AGCoreModuleInstance.getInstance().getEmailServerOrThrow(), fromAddressString);
	}

	public static IEmail createEmail(IEmailTransportServer server, String fromAddressString) {

		Session session = createSession(server);
		EmailTransport transport = createTransport(server, session);
		return new Email(session, transport, fromAddressString);
	}

	private static Properties getProperties(IEmailTransportServer server) {

		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", server.getAddress());
		properties.put("mail.smtp.port", server.getPort());
		properties.put("mail.smtp.user", server.getUsername());
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.trust", "*");
		return properties;
	}

	private static Session createSession(IEmailTransportServer server) {

		return Session.getInstance(getProperties(server));
	}

	private static EmailTransport createTransport(IEmailTransportServer server, Session session) {

		return new EmailTransport(server, () -> getTransport(session));
	}

	private static Transport getTransport(Session session) {

		try {
			return session.getTransport();
		} catch (NoSuchProviderException exception) {
			throw new RuntimeException(exception);
		}
	}
}
