package com.softicar.platform.core.module.start.page;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;

public class StartPageMessagesDiv extends DomDiv {

	public StartPageMessagesDiv() {

		AGStartPageMessage.getLatestMessages().forEach(this::appendMessage);
	}

	private void appendMessage(AGStartPageMessage message) {

		new DomWikiDivBuilder()//
			.addText(message.toDisplayWithoutId())
			.buildAndAppendTo(this);
	}
}
