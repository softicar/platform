package com.softicar.platform.common.ui.wiki.element.block;

import com.softicar.platform.common.ui.wiki.element.parser.WikiParentElementParser;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiParagraphParser extends WikiParentElementParser<WikiParagraph> {

	public WikiParagraphParser() {

		super(new WikiParagraph());

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
}
