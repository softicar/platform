package com.softicar.platform.common.ui.wiki.element.tag;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiToken;

public class WikiRawText extends AbstractWikiElement {

	private final WikiRawTextType type;
	private String text = "";
	private boolean blockElement;

	public WikiRawText(WikiRawTextType type) {

		this.type = type;
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return blockElement;
	}

	public WikiRawTextType getType() {

		return type;
	}

	public String getText() {

		return text;
	}

	// -------------------- protected -------------------- //

	protected void setText(WikiToken textToken) {

		this.blockElement = textToken.getConsumedText().contains("\n");

		this.text = textToken.getConsumedText();
		if (text.startsWith("\n")) {
			this.text = text.substring(1);
		}
	}
}
