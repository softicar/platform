package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.parser.WikiParentElementParser;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiHeadlineParser extends WikiParentElementParser<WikiHeadline> {

	public WikiHeadlineParser(WikiToken headlineToken) {

		super(new WikiHeadline(headlineToken));

		setEndTokenMatcher(token -> token.getTokenType() == WikiTokenType.HEADLINE);

		addChildTokenType(WikiTokenType.RESOURCE);
		addChildTokenType(WikiTokenType.TEXT);
	}
}
