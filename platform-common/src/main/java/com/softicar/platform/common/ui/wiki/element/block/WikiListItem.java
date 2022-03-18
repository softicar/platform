package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiParentElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiListItem extends AbstractWikiParentElement {

	private final WikiListType listType;
	private final int level;

	public WikiListItem(WikiToken token) {

		this.listType = determineListType(token);
		this.level = determineLevel(token);
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return true;
	}

	public WikiListType getListType() {

		return listType;
	}

	public int getLevel() {

		return level;
	}

	public boolean isChildListItem(WikiToken token) {

		if (token.getTokenType() == WikiTokenType.LIST_ITEM) {
			return determineLevel(token) > level;
		} else {
			return false;
		}
	}

	// -------------------- private -------------------- //

	private static WikiListType determineListType(WikiToken token) {

		String text = token.getConsumedText();
		if (text.endsWith("-")) {
			return WikiListType.ORDERED;
		} else if (text.endsWith("*")) {
			return WikiListType.UNORDERED;
		} else {
			throw new IllegalArgumentException(String.format("The list item prefix '%s' is invalid.", text));
		}
	}

	private static int determineLevel(WikiToken token) {

		return (token.getConsumedText().length() - 1) / 2;
	}
}
