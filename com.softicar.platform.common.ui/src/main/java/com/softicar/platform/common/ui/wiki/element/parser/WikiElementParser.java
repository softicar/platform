package com.softicar.platform.common.ui.wiki.element.parser;

import com.softicar.platform.common.ui.wiki.element.IWikiElement;
import com.softicar.platform.common.ui.wiki.parser.WikiTokenStream;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import java.util.function.Predicate;

public class WikiElementParser<E extends IWikiElement> implements IWikiElementParser<E> {

	protected final E element;
	protected Predicate<WikiToken> endTokenMatcher;

	public WikiElementParser(E element) {

		this.element = element;
		this.endTokenMatcher = token -> false;
	}

	@Override
	public E parse(WikiTokenStream tokenStream) {

		if (tokenStream.hasToken()) {
			if (endTokenMatcher.test(tokenStream.getToken())) {
				tokenStream.consumeToken();
			}
		}
		return element;
	}

	protected void setEndTokenMatcher(Predicate<WikiToken> endTokenMatcher) {

		this.endTokenMatcher = endTokenMatcher;
	}
}
