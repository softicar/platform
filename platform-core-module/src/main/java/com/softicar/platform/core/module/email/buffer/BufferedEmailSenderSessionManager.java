package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.core.module.server.AGServer;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import java.time.Duration;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class BufferedEmailSenderSessionManager {

	private final Map<AGServer, Session> sessionMap;

	public BufferedEmailSenderSessionManager() {

		this.sessionMap = new TreeMap<>();
	}

	public Session getSession(AGServer server) {

		return sessionMap.computeIfAbsent(server, this::createSession);
	}

	private Session createSession(AGServer server) {

		return Session.getInstance(new SessionProperties(server), new PasswordAuthenticator(server));
	}

	/**
	 * See <a href=
	 * "https://javaee.github.io/javamail/docs/api/index.html?com/sun/mail/smtp/package-summary.html">com.sun.mail.smtp
	 * properties documentation</a>.
	 */
	private static class SessionProperties extends Properties {

		private static final String SMTP_TIMEOUT = Duration.ofSeconds(30).toMillis() + "";

		public SessionProperties(AGServer server) {

			// connection
			put("mail.smtp.host", server.getAddress());
			put("mail.smtp.port", server.getPort());
			put("mail.smtp.user", server.getUsername());
			put("mail.transport.protocol", "smtp");

			// security
			put("mail.smtp.auth", "true");
			put("mail.smtp.ssl.trust", "*");
			put("mail.smtp.starttls.enable", "true");

			// timeouts
			put("mail.smtp.connectiontimeout", SMTP_TIMEOUT);
			put("mail.smtp.timeout", SMTP_TIMEOUT);
			put("mail.smtp.writetimeout", SMTP_TIMEOUT);
		}
	}

	private static class PasswordAuthenticator extends Authenticator {

		private final AGServer server;

		public PasswordAuthenticator(AGServer server) {

			this.server = server;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {

			return new PasswordAuthentication(server.getUsername(), server.getPassword());
		}
	}
}
