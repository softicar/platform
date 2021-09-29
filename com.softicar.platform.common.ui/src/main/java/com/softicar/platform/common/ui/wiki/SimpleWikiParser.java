package com.softicar.platform.common.ui.wiki;

import com.softicar.platform.common.string.scanning.SimpleTextScanner;
import com.softicar.platform.common.ui.wiki.matchers.WikiParserBoldMatcher;
import com.softicar.platform.common.ui.wiki.matchers.WikiParserColorMatcher;
import com.softicar.platform.common.ui.wiki.matchers.WikiParserHeadlineMatcher;
import com.softicar.platform.common.ui.wiki.matchers.WikiParserItalicMatcher;
import com.softicar.platform.common.ui.wiki.matchers.WikiParserListMatcher;
import com.softicar.platform.common.ui.wiki.matchers.WikiParserNewLineMatcher;
import com.softicar.platform.common.ui.wiki.matchers.WikiParserNewParagraphMatcher;

public class SimpleWikiParser {

	private final SimpleWikiParserEngine engine;
	private final SimpleTextScanner scanner;
	private final WikiParserListMatcher listMatcher;

	public SimpleWikiParser(ISimpleWikiParserCallback callback) {

		this.engine = new SimpleWikiParserEngine(callback);
		this.scanner = new SimpleTextScanner(engine);
		this.listMatcher = new WikiParserListMatcher(engine);

		scanner.addMatcher(new WikiParserBoldMatcher(engine));
		scanner.addMatcher(new WikiParserItalicMatcher(engine));
		scanner.addMatcher(new WikiParserColorMatcher(callback));
		scanner.addMatcher(new WikiParserNewLineMatcher(callback));
		scanner.addMatcher(new WikiParserNewParagraphMatcher(engine));
		scanner.addMatcher(new WikiParserHeadlineMatcher(engine));
		scanner.addMatcher(listMatcher);
	}

	public void parseLine(String line) {

		scanner.scan(line);
		scanner.scan("\n");
	}

	public void parseText(String text) {

		scanner.scan(text);
	}

	public void flush() {

		engine.flush();
	}
}
