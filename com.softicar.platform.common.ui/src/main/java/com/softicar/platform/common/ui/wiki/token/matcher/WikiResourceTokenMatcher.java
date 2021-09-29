package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiResourceToken;

public class WikiResourceTokenMatcher extends AbstractUrlTokenMatcher {

	public WikiResourceTokenMatcher() {

		super("{{", "}}", WikiResourceToken::new);
	}
}
