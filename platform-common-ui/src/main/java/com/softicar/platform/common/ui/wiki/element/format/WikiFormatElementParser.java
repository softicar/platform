package com.softicar.platform.common.ui.wiki.element.format;

import com.softicar.platform.common.ui.wiki.element.parser.WikiParentElementParser;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiFormatElementParser<E extends AbstractWikiFormatElement> extends WikiParentElementParser<E> {

	public WikiFormatElementParser(E element) {

		super(element);

		setEndTokenMatcher(token -> token.getTokenType() == element.getTokenType());

		addChildTokenType(WikiTokenType.BOLD);
		addChildTokenType(WikiTokenType.RESOURCE);
		addChildTokenType(WikiTokenType.ITALIC);
		addChildTokenType(WikiTokenType.MONOSPACE);
		addChildTokenType(WikiTokenType.TEXT);
		addChildTokenType(WikiTokenType.UNDERLINE);
		addChildTokenType(WikiTokenType.URL);
	}
}
