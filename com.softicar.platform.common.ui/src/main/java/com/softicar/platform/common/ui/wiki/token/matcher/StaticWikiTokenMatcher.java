package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;

public class StaticWikiTokenMatcher implements IWikiTokenMatcher {

	private final WikiTokenType tokenType;
	private final String tokenText;

	public StaticWikiTokenMatcher(WikiTokenType tokenType, String tokenText) {

		this.tokenType = tokenType;
		this.tokenText = tokenText;
	}

	@Override
	public boolean match(IWikiTokenizer tokenizer) {

		if (tokenizer.startsWith(tokenText)) {
			tokenizer.addToken(new WikiToken(tokenType, tokenText));
			return true;
		} else {
			return false;
		}
	}
}
