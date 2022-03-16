package com.softicar.platform.common.ui.wiki.element.inline;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;

public class WikiLineBreak extends AbstractWikiElement {

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return false;
	}
}
