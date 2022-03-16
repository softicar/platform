package com.softicar.platform.common.ui.wiki.token.matcher;

public class MainWikiTokenMatcher extends WikiTokenMatcherList {

	public MainWikiTokenMatcher() {

		addMatcher(WikiTokenMatchers.BOLD_MATCHER);
		addMatcher(WikiTokenMatchers.BACKTICK_MATCHER);
		addMatcher(WikiTokenMatchers.SINGLE_QUOTES_MATCHER);
		addMatcher(WikiTokenMatchers.HEADLINE_MATCHER);
		addMatcher(WikiTokenMatchers.RESOURCE_MATCHER);
		addMatcher(WikiTokenMatchers.ITALIC_MATCHER);
		addMatcher(WikiTokenMatchers.LINE_BREAK_MATCHER);
		addMatcher(WikiTokenMatchers.LIST_ITEM_MATCHER);
		addMatcher(WikiTokenMatchers.PARAGRAPH_MATCHER);
		addMatcher(WikiTokenMatchers.TABLE_ROW_MATCHER);
		addMatcher(WikiTokenMatchers.TAG_MATCHER);
		addMatcher(WikiTokenMatchers.UNDERLINE_MATCHER);
		addMatcher(WikiTokenMatchers.URL_MATCHER);
	}
}
