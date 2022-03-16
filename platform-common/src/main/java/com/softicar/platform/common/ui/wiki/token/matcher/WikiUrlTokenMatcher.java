package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiUrlToken;

public class WikiUrlTokenMatcher extends AbstractUrlTokenMatcher {

	public WikiUrlTokenMatcher() {

		super("[[", "]]", WikiUrlToken::new);
	}
}
