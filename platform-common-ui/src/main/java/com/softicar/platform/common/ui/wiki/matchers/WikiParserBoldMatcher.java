package com.softicar.platform.common.ui.wiki.matchers;

import com.softicar.platform.common.string.scanning.AbstractLiteralMatcher;
import com.softicar.platform.common.ui.wiki.SimpleWikiParserEngine;

public class WikiParserBoldMatcher extends AbstractLiteralMatcher {

	private final SimpleWikiParserEngine engine;
	private boolean bold;

	public WikiParserBoldMatcher(SimpleWikiParserEngine engine) {

		super("**");

		this.engine = engine;
		this.bold = false;
	}

	@Override
	public void consumeLiteral(String literal) {

		bold = !bold;
		if (bold) {
			engine.beginBold();
		} else {
			engine.endBold();
		}
	}
}
