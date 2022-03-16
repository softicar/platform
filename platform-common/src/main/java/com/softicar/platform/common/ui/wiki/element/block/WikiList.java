package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiParentElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;

public class WikiList extends AbstractWikiParentElement {

	private final WikiListType listType;
	private final int level;

	public WikiList(WikiListItem listItem) {

		this.listType = listItem.getListType();
		this.level = listItem.getLevel();
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

	public boolean isCompatible(WikiListItem listItem) {

		return listItem.getListType() == listType && listItem.getLevel() == level;
	}
}
