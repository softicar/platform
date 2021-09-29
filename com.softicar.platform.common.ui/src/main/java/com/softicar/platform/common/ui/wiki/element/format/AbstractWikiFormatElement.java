package com.softicar.platform.common.ui.wiki.element.format;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiParentElement;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public abstract class AbstractWikiFormatElement extends AbstractWikiParentElement {

	private final WikiTokenType tokenType;

	public AbstractWikiFormatElement(WikiTokenType tokenType) {

		this.tokenType = tokenType;
	}

	public WikiTokenType getTokenType() {

		return tokenType;
	}

	@Override
	public boolean isBlockElement() {

		return false;
	}
}
