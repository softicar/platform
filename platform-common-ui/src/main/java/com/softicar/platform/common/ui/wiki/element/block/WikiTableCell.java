package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;

public class WikiTableCell extends AbstractWikiElement {

	private final String text;
	private final boolean header;

	public WikiTableCell(String text, boolean header) {

		this.text = text;
		this.header = header;
	}

	@Override
	public void accept(IWikiVisitor visitor) {

		visitor.visit(this);
	}

	@Override
	public boolean isBlockElement() {

		return true;
	}

	public String getText() {

		return text;
	}

	public boolean isHeader() {

		return header;
	}
}
