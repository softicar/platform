package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnection;
import com.softicar.platform.core.module.email.mailbox.IMailboxMessage;
import com.sun.mail.imap.IMAPFolder;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ImapConnection implements IMailboxConnection {

	private final Map<String, Folder> folders;
	private final Supplier<Store> storeFactory;
	private Store store;

	public ImapConnection(Supplier<Store> storeFactory) {

		this.folders = new TreeMap<>();
		this.storeFactory = storeFactory;
	}

	@Override
	public void close() {

		new ResourceCloser().closeAll();
	}

	@Override
	@SuppressWarnings("resource")
	public Collection<IMailboxMessage> getMessagesInFolder(String folderName) {

		try {
			return Stream//
				.of(getOpenFolder(folderName).getMessages())
				.map(message -> new ImapMessage(this, message))
				.collect(Collectors.toList());
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	@SuppressWarnings("resource")
	public void copyMessageTo(Message message, String targetFolderName) {

		try {
			getOpenFolder(message.getFolder().getFullName()).copyMessages(new Message[] { message }, getOpenFolder(targetFolderName));
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	@SuppressWarnings("resource")
	public void moveMessageTo(Message message, String targetFolderName) {

		try {
			var imapFolder = (IMAPFolder) getOpenFolder(message.getFolder().getFullName());
			imapFolder.moveMessages(new Message[] { message }, getOpenFolder(targetFolderName));
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
			this.store = storeFactory.get();
		}
		return store;
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
