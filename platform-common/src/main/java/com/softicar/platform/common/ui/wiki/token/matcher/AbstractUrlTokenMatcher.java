package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;
import java.util.Optional;
import java.util.function.Function;

public class AbstractUrlTokenMatcher implements IWikiTokenMatcher {

	private final String begin;
	private final String end;
	private final Function<String, WikiToken> tokenFactory;

	public AbstractUrlTokenMatcher(String begin, String end, Function<String, WikiToken> tokenFactory) {

		this.begin = begin;
		this.end = end;
		this.tokenFactory = tokenFactory;
	}

	@Override
	public boolean match(IWikiTokenizer tokenizer) {

		if (tokenizer.startsWith(begin)) {
			Optional<Integer> endIndex = tokenizer.findIndexOf(end);
			if (endIndex.isPresent()) {
				String text = tokenizer.getSubstring(0, endIndex.get() + 2);
				tokenizer.addToken(tokenFactory.apply(text));
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
