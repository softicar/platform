package com.softicar.platform.common.ui.wiki.element.tag;

import com.softicar.platform.common.ui.wiki.element.parser.WikiElementParser;
import com.softicar.platform.common.ui.wiki.parser.WikiTokenStream;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiRawTextParser extends WikiElementParser<WikiRawText> {

	public WikiRawTextParser(WikiRawTextType type) {

		super(new WikiRawText(type));
	}

	@Override
	public WikiRawText parse(WikiTokenStream tokenStream) {

		while (tokenStream.hasToken()) {
			WikiToken token = tokenStream.getToken();
			if (token.getTokenType() == WikiTokenType.TEXT) {
				element.setText(token);
				tokenStream.consumeToken();
			} else if (token.isCloseTag(element.getType().getTag())) {
				tokenStream.consumeToken();
				break;
			} else {
				break;
			}
		}
		return element;
	}
}
