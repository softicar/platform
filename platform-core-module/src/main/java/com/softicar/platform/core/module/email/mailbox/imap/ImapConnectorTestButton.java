package com.softicar.platform.core.module.email.mailbox.imap;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.email.mailbox.IMailboxConnection;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.dom.elements.button.DomButton;

class ImapConnectorTestButton extends DomButton {

	private final AGServer server;

	public ImapConnectorTestButton(AGServer server) {

		this.server = server;

		setLabel(CoreI18n.TEST_CONNECTION);
		setIcon(CoreImages.EXECUTE.getResource());
		setClickCallback(this::test);
	}

	private void test() {

		try (var connection = IMailboxConnection.getInstance(server)) {
			var count = connection.getMessagesInFolder("inbox").size();
			executeAlert(CoreI18n.CONNECTION_SUCCESSFULL.concatSentence(CoreI18n.ARG1_MESSAGES_FOUND_IN_INBOX.toDisplay(count)));
		} catch (Exception exception) {
			throw new SofticarUserException(exception, CoreI18n.CONNECTION_FAILED.concat(exception.getMessage()));
		}
	}
}
