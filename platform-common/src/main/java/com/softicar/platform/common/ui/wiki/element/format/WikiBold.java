package com.softicar.platform.common.ui.wiki.element.format;

import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiBold extends AbstractWikiFormatElement {

	public WikiBold() {

		super(WikiTokenType.BOLD);
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}
}
