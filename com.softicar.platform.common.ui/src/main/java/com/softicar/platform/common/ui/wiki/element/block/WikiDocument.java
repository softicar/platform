package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiParentElement;
import com.softicar.platform.common.ui.wiki.element.IWikiElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;

public class WikiDocument extends AbstractWikiParentElement {

	@Override
	public void addChild(IWikiElement child) {

		if (child.isBlockElement()) {
			super.addChild(child);
		} else {
			getOrAddChildOfType(WikiParagraph.class, WikiParagraph::new).addChild(child);
		}
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return true;
	}
}
