package com.softicar.platform.core.module.start.page;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;

public class StartPageMessagesDiv extends DomDiv {

	private static final int DEFAULT_MESSAGE_AMOUNT_TO_LOAD = 5;

	public StartPageMessagesDiv() {

		AGStartPageMessage.getLatestMessages(DEFAULT_MESSAGE_AMOUNT_TO_LOAD).forEach(this::appendMessage);
	}

	private void appendMessage(AGStartPageMessage message) {

		new DomWikiDivBuilder()//
			.addText(message.toDisplayWithoutId())
			.buildAndAppendTo(this);
	}
}
