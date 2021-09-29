package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.AbstractWikiParentElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.token.WikiTableRowToken;
import com.softicar.platform.common.ui.wiki.token.WikiTableRowToken.Cell;

public class WikiTableRow extends AbstractWikiParentElement {

	public WikiTableRow(WikiTableRowToken token) {

		for (Cell cell: token.getCells()) {
			addChild(new WikiTableCell(cell.getText(), cell.isHeader()));
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
