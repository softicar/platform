package com.softicar.platform.common.ui.wiki.element.inline;

import com.softicar.platform.common.string.Trim;
import com.softicar.platform.common.ui.wiki.element.AbstractWikiElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiToken;

public class WikiText extends AbstractWikiElement {

	private final String rawText;
	private String text;

	public WikiText(WikiToken token) {

		this.rawText = token.getConsumedText();
		this.text = new WikiWhitespaceNormalizer(token.getConsumedText()).getNormalized();
	}

	@Override
	public String toString() {

		return new StringBuilder()//
			.append(getClass().getSimpleName())
			.append("(")
			.append(text)
			.append(")")
			.toString();
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return false;
	}

	public void trimLeft() {

		this.text = Trim.trimLeft(text);
	}

	public void trimRight() {

		this.text = Trim.trimRight(text);
	}

	public String getText() {

		return text;
	}

	public String getRawText() {

		return rawText;
	}
}
