package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiTokenType;

public class WikiTokenMatchers {

	public static final IWikiTokenMatcher BOLD_MATCHER = new StaticWikiTokenMatcher(WikiTokenType.BOLD, "**");
	public static final IWikiTokenMatcher BACKTICK_MATCHER = new StaticWikiTokenMatcher(WikiTokenType.MONOSPACE, "`");
	public static final IWikiTokenMatcher SINGLE_QUOTES_MATCHER = new StaticWikiTokenMatcher(WikiTokenType.MONOSPACE, "''");
	public static final IWikiTokenMatcher HEADLINE_MATCHER = new WikiHeadlineMatcher();
	public static final IWikiTokenMatcher ITALIC_MATCHER = new StaticWikiTokenMatcher(WikiTokenType.ITALIC, "//");
	public static final IWikiTokenMatcher LINE_BREAK_MATCHER = new StaticWikiTokenMatcher(WikiTokenType.LINE_BREAK, "\\\\ ");
	public static final IWikiTokenMatcher LIST_ITEM_MATCHER = new WikiListItemMatcher();
	public static final IWikiTokenMatcher PARAGRAPH_MATCHER = new WikiParagraphMatcher();
	public static final IWikiTokenMatcher RESOURCE_MATCHER = new WikiResourceTokenMatcher();
	public static final IWikiTokenMatcher TABLE_ROW_MATCHER = new WikiTableRowMatcher();
	public static final IWikiTokenMatcher TAG_MATCHER = new WikiTagMatcher();
	public static final IWikiTokenMatcher UNDERLINE_MATCHER = new StaticWikiTokenMatcher(WikiTokenType.UNDERLINE, "__");
	public static final IWikiTokenMatcher URL_MATCHER = new WikiUrlTokenMatcher();
}
