package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;
import java.util.ArrayList;

public class WikiTokenMatcherList implements IWikiTokenMatcher {

	private final ArrayList<IWikiTokenMatcher> matchers;

	public WikiTokenMatcherList() {

		this.matchers = new ArrayList<>();
	}

	public WikiTokenMatcherList addMatcher(IWikiTokenMatcher matcher) {

		matchers.add(matcher);
		return this;
	}

	@Override
	public boolean match(IWikiTokenizer tokenizer) {

		for (IWikiTokenMatcher matcher: matchers) {
			if (matcher.match(tokenizer)) {
				return true;
			}
		}
		return false;
	}
}
