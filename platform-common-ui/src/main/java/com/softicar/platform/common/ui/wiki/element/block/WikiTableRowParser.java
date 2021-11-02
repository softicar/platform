package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.parser.WikiParentElementParser;
import com.softicar.platform.common.ui.wiki.token.WikiTableRowToken;
import com.softicar.platform.common.ui.wiki.token.WikiToken;

public class WikiTableRowParser extends WikiParentElementParser<WikiTableRow> {

	public WikiTableRowParser(WikiTableRowToken token) {

		super(new WikiTableRow(token));
	}

	@Override
	public boolean isChildToken(WikiToken token) {

		return super.isChildToken(token);
	}
}
