package com.softicar.platform.common.ui.wiki.parser;

import com.softicar.platform.common.ui.wiki.token.WikiToken;
import java.util.List;

public class WikiTokenStream {

	private final List<WikiToken> tokens;
	private int index;

	public WikiTokenStream(List<WikiToken> tokens) {

		this.tokens = tokens;
		this.index = 0;
	}

	public boolean hasToken() {

		return index < tokens.size();
	}

	public WikiToken getToken() {

		return tokens.get(index);
	}

	public void consumeToken() {

		index++;
	}
}
