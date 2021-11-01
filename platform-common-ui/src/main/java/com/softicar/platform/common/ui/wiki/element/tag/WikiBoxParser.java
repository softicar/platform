package com.softicar.platform.common.ui.wiki.element.tag;

import com.softicar.platform.common.ui.wiki.element.parser.WikiParentElementParser;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiBoxParser extends WikiParentElementParser<WikiBox> {

	public WikiBoxParser(WikiBox element) {

		super(element);

		setEndTokenMatcher(token -> token.isCloseTag(element.getBoxType().getTag()));

		addChildTokenType(WikiTokenType.BOLD);
		addChildTokenType(WikiTokenType.RESOURCE);
		addChildTokenType(WikiTokenType.ITALIC);
		addChildTokenType(WikiTokenType.LINE_BREAK);
		addChildTokenType(WikiTokenType.LIST_ITEM);
		addChildTokenType(WikiTokenType.MONOSPACE);
		addChildTokenType(WikiTokenType.PARAGRAPH);
		addChildTokenType(WikiTokenType.TAG);
		addChildTokenType(WikiTokenType.TEXT);
		addChildTokenType(WikiTokenType.UNDERLINE);
		addChildTokenType(WikiTokenType.URL);
	}
}
