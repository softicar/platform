package com.softicar.platform.common.ui.wiki.parser;

import com.softicar.platform.common.ui.wiki.element.block.WikiDocument;
import com.softicar.platform.common.ui.wiki.element.parser.WikiParentElementParser;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.tokenizer.WikiTokenizer;
import java.util.List;

public class WikiParser extends WikiParentElementParser<WikiDocument> {

	public WikiParser() {

		super(new WikiDocument());
	}

	public WikiDocument parse(String wikiText) {

		return parse(new WikiTokenizer(wikiText).tokenize());
	}

	public WikiDocument parse(List<WikiToken> tokens) {

		WikiDocument document = parse(new WikiTokenStream(tokens));
		document.cleanup();
		return document;
	}

	@Override
	protected boolean isChildToken(WikiToken token) {

		return true;
	}
}
