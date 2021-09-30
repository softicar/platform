package com.softicar.platform.common.ui.wiki.matchers;

import com.softicar.platform.common.string.scanning.AbstractLiteralMatcher;
import com.softicar.platform.common.ui.wiki.SimpleWikiParserEngine;

public class WikiParserNewParagraphMatcher extends AbstractLiteralMatcher {

	private final SimpleWikiParserEngine engine;

	public WikiParserNewParagraphMatcher(SimpleWikiParserEngine engine) {

		super("\n\n");
		this.engine = engine;
	}

	@Override
	public void consumeLiteral(String literal) {

		engine.endParagraph();
	}
}
