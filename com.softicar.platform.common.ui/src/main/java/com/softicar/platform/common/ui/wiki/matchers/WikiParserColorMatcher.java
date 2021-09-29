package com.softicar.platform.common.ui.wiki.matchers;

import com.softicar.platform.common.string.scanning.ISimpleTextMatcher;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.common.ui.color.RgbColor;
import com.softicar.platform.common.ui.wiki.ISimpleWikiParserCallback;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiParserColorMatcher implements ISimpleTextMatcher {

	private static final String COLOR_BEGIN = "<color";
	private static final String COLOR_END = "</color>";
	private static final Pattern COLOR_BEGIN_PATTERN = Pattern.compile("<color\\s+(#[0-9a-fA-F]+)>");
	private final ISimpleWikiParserCallback callback;
	private String colorCode;

	public WikiParserColorMatcher(ISimpleWikiParserCallback callback) {

		this.callback = callback;
	}

	@Override
	public int getMatchingLength(String text) {

		if (text.startsWith(COLOR_BEGIN)) {
			Matcher matcher = COLOR_BEGIN_PATTERN.matcher(text);
			if (matcher.lookingAt()) {
				this.colorCode = matcher.group(1);
				return matcher.group().length();
			} else {
				return 0;
			}
		} else if (text.startsWith(COLOR_END)) {
			return COLOR_END.length();
		} else {
			return 0;
		}
	}

	@Override
	public void consumeMatchingText(String text) {

		if (text.startsWith(COLOR_BEGIN)) {
			IColor color = RgbColor.parseHtmlCode(colorCode);
			callback.beginColor(color);
		} else if (text.equals(COLOR_END)) {
			callback.endColor();
		} else {
			throw new IllegalArgumentException(String.format("Text '%s' is not matching <color> pattern.", text));
		}
	}
}
