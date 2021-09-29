package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;

public class WikiParagraphMatcher implements IWikiTokenMatcher {

	@Override
	public boolean match(IWikiTokenizer tokenizer) {

		if (tokenizer.startsWith("\n\n")) {
			int end = tokenizer.findIndexOfOther('\n', 2);
			tokenizer.addToken(new WikiToken(WikiTokenType.PARAGRAPH, tokenizer.getSubstring(0, end)));
			return true;
		} else {
			return false;
		}
	}
}
