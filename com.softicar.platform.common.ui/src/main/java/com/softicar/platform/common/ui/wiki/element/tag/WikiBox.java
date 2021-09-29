package com.softicar.platform.common.ui.wiki.element.tag;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiParentElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;

public class WikiBox extends AbstractWikiParentElement {

	private final WikiBoxType boxType;

	public WikiBox(WikiBoxType boxType) {

		this.boxType = boxType;
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return true;
	}

	public WikiBoxType getBoxType() {

		return boxType;
	}
}
