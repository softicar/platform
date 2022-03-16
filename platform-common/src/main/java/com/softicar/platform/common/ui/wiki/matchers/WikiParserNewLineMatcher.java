package com.softicar.platform.common.ui.wiki.matchers;

import com.softicar.platform.common.string.scanning.AbstractLiteralMatcher;
import com.softicar.platform.common.ui.wiki.ISimpleWikiParserCallback;

public class WikiParserNewLineMatcher extends AbstractLiteralMatcher {

	private final ISimpleWikiParserCallback callback;

	public WikiParserNewLineMatcher(ISimpleWikiParserCallback callback) {

		super("\\\\");
		this.callback = callback;
	}

	@Override
	public void consumeLiteral(String literal) {

		callback.addNewLine();
	}
}
