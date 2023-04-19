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

		public SessionProperties(AGServer server) {

			// basics
			put("mail.smtp.host", server.getAddress());
			put("mail.smtp.port", server.getPort());
			put("mail.smtp.user", server.getUsername());
			put("mail.transport.protocol", "smtp");

			// timeouts
			put("mail.smtp.connectiontimeout", Duration.ofSeconds(30).toMillis());
			put("mail.smtp.timeout", Duration.ofSeconds(30).toMillis());
			put("mail.smtp.writetimeout", Duration.ofSeconds(30).toMillis());

			// security
			put("mail.smtp.auth", "true");
			put("mail.smtp.ssl.trust", "*");
			put("mail.smtp.starttls.enable", "true");
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
