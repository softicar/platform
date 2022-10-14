package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.common.core.exception.ExceptionsCollector;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnection;
import com.softicar.platform.core.module.email.mailbox.IMailboxMessage;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;

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

	@SuppressWarnings("resource")
	public void copyMessageTo(Message message, String targetFolderName) {

		try {
			getOpenFolder(message.getFolder().getFullName()).copyMessages(new Message[] { message }, getOpenFolder(targetFolderName));
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	public void moveMessageTo(Message message, String targetFolderName) {

		try {
			copyMessageTo(message, targetFolderName);
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
