package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.parser.WikiParentElementParser;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiListItemParser extends WikiParentElementParser<WikiListItem> {

	public WikiListItemParser(WikiToken token) {

		super(new WikiListItem(token));

		addChildTokenType(WikiTokenType.BOLD);
		addChildTokenType(WikiTokenType.RESOURCE);
		addChildTokenType(WikiTokenType.ITALIC);
		addChildTokenType(WikiTokenType.LINE_BREAK);
		addChildTokenType(WikiTokenType.MONOSPACE);
		addChildTokenType(WikiTokenType.TAG);
		addChildTokenType(WikiTokenType.TEXT);
		addChildTokenType(WikiTokenType.UNDERLINE);
		addChildTokenType(WikiTokenType.URL);
	}

	@Override
	public boolean isChildToken(WikiToken token) {

		if (element.isChildListItem(token)) {
			return true;
		} else {
			return super.isChildToken(token);
		}
	}
}
