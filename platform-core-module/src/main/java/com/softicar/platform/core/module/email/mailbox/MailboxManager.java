package com.softicar.platform.core.module.email.mailbox;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class MailboxManager implements AutoCloseable {

	private static final int TIMEOUT_MILLIS = 10000;
	private static final int CONNECTION_TIMEOUT_MILLIS = 10000;
	private final IMailbox mailbox;
	private final Map<String, Folder> folders;
	private Store store;

	public MailboxManager(IMailbox mailbox) {

		this.mailbox = mailbox;
		this.folders = new TreeMap<>();
	}

	@Override
	public void close() {

		new ResourceCloser().closeAll();
	}

	@SuppressWarnings("resource")
	public Collection<Message> getMessagesInFolder(String folderName) {

		try {
			return Arrays.asList(getOpenFolder(folderName).getMessages());
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@SuppressWarnings("resource")
	public void moveMessages(Message message, String sourceFolderName, String targetFolderName) {

		try {
			getOpenFolder(sourceFolderName).copyMessages(new Message[] { message }, getOpenFolder(targetFolderName));
			message.setFlag(Flags.Flag.DELETED, true);
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@SuppressWarnings("resource")
	private Folder getOpenFolder(String name) {

		Folder folder = folders.get(name);
		if (folder == null) {
			folders.put(name, folder = openFolder(name));
		}
		return folder;
	}

	@SuppressWarnings("resource")
	private Folder openFolder(String name) {

		try {
			Folder folder = getStore().getFolder(name);
			folder.open(Folder.READ_WRITE);
			return folder;
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Store getStore() {

		if (store == null) {
			this.store = openStore();
		}
		return store;
	}

	private Store openStore() {

		try {
			Properties properties = setupProperties();
			Session session = Session.getInstance(properties);
			Store store = session.getStore(mailbox.getProtocol());
			store.connect(mailbox.getServer(), mailbox.getUsername(), mailbox.getPassword());
			return store;
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Properties setupProperties() {

		String protocol = mailbox.getProtocol();

		// FIXME remove the trust all!!!
		Properties properties = new Properties();
		properties.setProperty("mail.imaps.ssl.trust", "*");
		properties.setProperty("mail.store.protocol", protocol);
		properties.setProperty(String.format("mail.%s.connectiontimeout", protocol), String.valueOf(CONNECTION_TIMEOUT_MILLIS));
		properties.setProperty(String.format("mail.%s.timeout", protocol), String.valueOf(TIMEOUT_MILLIS));
		return properties;
	}

	private class ResourceCloser {
	
		private final ExceptionsCollector collector;
	
		public ResourceCloser() {
	
			this.collector = new ExceptionsCollector();
		}
	
		public void closeAll() {
	
			closeFolders();
			closeStore();
			collector.throwExceptionIfNotEmpty();
		}
	
		private void closeFolders() {
	
			try {
				for (Folder folder: folders.values()) {
					folder.close(true);
				}
				folders.clear();
			} catch (MessagingException exception) {
				collector.add(exception);
			}
		}
	
		private void closeStore() {
	
			try {
				if (store != null) {
					store.close();
					store = null;
				}
			} catch (MessagingException exception) {
				collector.add(exception);
			}
		}
	}
}
