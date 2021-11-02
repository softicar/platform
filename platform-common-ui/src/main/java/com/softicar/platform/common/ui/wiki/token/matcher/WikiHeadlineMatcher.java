package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;

public class WikiHeadlineMatcher implements IWikiTokenMatcher {

	@Override
	public boolean match(IWikiTokenizer tokenizer) {

		if (tokenizer.startsWith("==")) {
			int end = tokenizer.findIndexOfOther('=', 2);
			String text = tokenizer.getSubstring(0, end);
			tokenizer.addToken(new WikiToken(WikiTokenType.HEADLINE, text));
			return true;
		}
		return false;
	}
}
