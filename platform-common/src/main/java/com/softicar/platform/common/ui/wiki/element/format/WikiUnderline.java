package com.softicar.platform.common.ui.wiki.element.format;

import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiUnderline extends AbstractWikiFormatElement {

	public WikiUnderline() {

		super(WikiTokenType.UNDERLINE);
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}
}
