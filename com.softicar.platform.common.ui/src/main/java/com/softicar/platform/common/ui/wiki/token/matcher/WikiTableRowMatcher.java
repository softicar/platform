package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiTableRowToken;
import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;

public class WikiTableRowMatcher implements IWikiTokenMatcher {

	@Override
	public boolean match(IWikiTokenizer tokenizer) {

		if (tokenizer.getCharAt(-1, '\n') == '\n') {
			if (tokenizer.startsWith("^") || tokenizer.startsWith("|")) {
				int end = tokenizer//
					.findIndexOf("\n")
					.map(index -> index + 1)
					.orElse(tokenizer.getLength());
				String text = tokenizer.getSubstring(0, end);
				tokenizer.addToken(new WikiTableRowToken(text));
				return true;
			}
		}
		return false;
	}
}
