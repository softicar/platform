package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiParentElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiToken;

public class WikiHeadline extends AbstractWikiParentElement {

	private final int level;

	public WikiHeadline(WikiToken headlineToken) {

		this.level = determineLevel(headlineToken);
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return true;
	}

	public int getLevel() {

		return level;
	}

	// -------------------- private -------------------- //

	private int determineLevel(WikiToken headlineToken) {

		return Math.min(headlineToken.getConsumedText().length(), 6);
	}
}
