package com.softicar.platform.common.ui.wiki.matchers;

import com.softicar.platform.common.string.scanning.ISimpleTextMatcher;
import com.softicar.platform.common.ui.wiki.SimpleWikiParserEngine;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiParserListMatcher implements ISimpleTextMatcher {

	private static final Pattern LIST_PREFIX_PATTERN = Pattern.compile("  [ ]*[\\*-]\\s+");
	private final SimpleWikiParserEngine engine;

	public WikiParserListMatcher(SimpleWikiParserEngine engine) {

		this.engine = engine;
	}

	@Override
	public int getMatchingLength(String text) {

		Matcher matcher = LIST_PREFIX_PATTERN.matcher(text);
		if (matcher.lookingAt()) {
			return matcher.group().length();
		} else if (engine.isInList() && text.startsWith("\n")) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public void consumeMatchingText(String text) {

		if (text.equals("\n")) {
			engine.endListItem();
		} else {
			while (!Objects.equals(engine.getCurrentList(), text)) {
				String top = engine.getCurrentList();
				if (top == null || top.length() < text.length()) {
					engine.beginList(text);
				} else if (top.length() >= text.length()) {
					engine.endCurrentList();
				}
			}

			engine.beginListItem();
		}
	}
}
