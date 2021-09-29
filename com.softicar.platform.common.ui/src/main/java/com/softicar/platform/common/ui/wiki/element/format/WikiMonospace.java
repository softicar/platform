package com.softicar.platform.common.ui.wiki.element.format;

import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiMonospace extends AbstractWikiFormatElement {

	public WikiMonospace() {

		super(WikiTokenType.MONOSPACE);
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}
}
