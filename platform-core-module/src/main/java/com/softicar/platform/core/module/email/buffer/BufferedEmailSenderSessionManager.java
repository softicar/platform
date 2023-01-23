package com.softicar.platform.core.module.email.buffer;

import com.softicar.platform.core.module.server.AGServer;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
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

	private static class SessionProperties extends Properties {

		public SessionProperties(AGServer server) {

			put("mail.transport.protocol", "smtp");
			put("mail.smtp.starttls.enable", "true");
//			put("mail.smtp.starttls.required", "true");
			put("mail.smtp.host", server.getAddress());
			put("mail.smtp.port", server.getPort());
			put("mail.smtp.user", server.getUsername());
			put("mail.smtp.auth", "true");
			put("mail.smtp.ssl.trust", "*");
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
