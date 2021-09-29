package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;

@FunctionalInterface
public interface IWikiTokenMatcher {

	boolean match(IWikiTokenizer tokenizer);
}
