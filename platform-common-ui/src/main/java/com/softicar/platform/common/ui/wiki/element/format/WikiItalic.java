package com.softicar.platform.common.ui.wiki.element.format;

import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiItalic extends AbstractWikiFormatElement {

	public WikiItalic() {

		super(WikiTokenType.ITALIC);
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}
}
