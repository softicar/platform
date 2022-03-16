package com.softicar.platform.common.ui.wiki.matchers;

import com.softicar.platform.common.string.scanning.AbstractLiteralMatcher;
import com.softicar.platform.common.ui.wiki.SimpleWikiParserEngine;

public class WikiParserItalicMatcher extends AbstractLiteralMatcher {

	private final SimpleWikiParserEngine engine;
	private boolean italic;

	public WikiParserItalicMatcher(SimpleWikiParserEngine engine) {

		super("//");

		this.engine = engine;
		this.italic = false;
	}

	@Override
	public void consumeLiteral(String literal) {

		italic = !italic;
		if (italic) {
			engine.beginItalic();
		} else {
			engine.endItalic();
		}
	}
}
