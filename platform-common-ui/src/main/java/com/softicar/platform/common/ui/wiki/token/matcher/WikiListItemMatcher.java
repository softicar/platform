package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;

public class WikiListItemMatcher implements IWikiTokenMatcher {

	@Override
	public boolean match(IWikiTokenizer tokenizer) {

		if (tokenizer.startsWith("  ")) {
			int end = tokenizer.findIndexOfOther(' ', 2);
			char c = tokenizer.getCharAt(end, ' ');
			if (c == '*' || c == '-') {
				String tokenText = tokenizer.getSubstring(0, end + 1);
				tokenizer.addToken(new WikiToken(WikiTokenType.LIST_ITEM, tokenText));
				return true;
			}
		}
		return false;
	}
}
