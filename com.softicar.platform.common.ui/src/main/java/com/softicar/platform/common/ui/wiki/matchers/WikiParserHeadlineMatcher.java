package com.softicar.platform.common.ui.wiki.matchers;

import com.softicar.platform.common.string.scanning.AbstractLiteralMatcher;
import com.softicar.platform.common.ui.wiki.SimpleWikiParserEngine;

public class WikiParserHeadlineMatcher extends AbstractLiteralMatcher {

	private final SimpleWikiParserEngine engine;
	private boolean headline;

	public WikiParserHeadlineMatcher(SimpleWikiParserEngine engine) {

		this.engine = engine;
		this.headline = false;

		addLiteral("======");
		addLiteral("=====");
		addLiteral("====");
		addLiteral("===");
		addLiteral("==");
	}

	@Override
	protected void consumeLiteral(String literal) {

		this.headline = !headline;

		int level = 7 - literal.length();
		if (headline) {
			engine.beginHeadLine(level);
		} else {
			engine.endHeadLine(level);
		}
	}
}
