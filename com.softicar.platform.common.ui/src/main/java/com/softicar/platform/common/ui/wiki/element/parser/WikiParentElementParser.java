package com.softicar.platform.common.ui.wiki.element.parser;

import com.softicar.platform.common.ui.wiki.element.IWikiParentElement;
import com.softicar.platform.common.ui.wiki.parser.WikiTokenStream;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import java.util.Set;
import java.util.TreeSet;

public class WikiParentElementParser<E extends IWikiParentElement> extends WikiElementParser<E> {

	private final Set<WikiTokenType> childTokenTypes;

	public WikiParentElementParser(E element) {

		super(element);

		this.childTokenTypes = new TreeSet<>();
	}

	@Override
	public E parse(WikiTokenStream tokenStream) {

		while (tokenStream.hasToken()) {
			WikiToken token = tokenStream.getToken();
			if (endTokenMatcher.test(token)) {
				tokenStream.consumeToken();
				break;
			} else if (isChildToken(token)) {
				tokenStream.consumeToken();
				element.addChild(WikiElementParserFactory.getInstance().createParser(token).parse(tokenStream));
			} else {
				break;
			}
		}
		return element;
	}

	protected boolean isChildToken(WikiToken token) {

		if (token.isCloseTag()) {
			return false;
		} else {
			return childTokenTypes.contains(token.getTokenType());
		}
	}

	protected void addChildTokenType(WikiTokenType childTokenType) {

		this.childTokenTypes.add(childTokenType);
	}
}
