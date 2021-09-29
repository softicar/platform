package com.softicar.platform.common.string.scanning;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple scanner that splits the text into normal text and keys.
 * 
 * @author Oliver Richers
 */
public class SimpleTextScanner {

	private final ISimpleTextScannerCallback callback;
	private final List<ISimpleTextMatcher> matchers;
	private String text;
	private final StringBuilder normalText;
	private final StringBuilder whitespace;
	private int index;
	private ISimpleTextMatcher currentMatcher;
	private int matchingLength;

	public SimpleTextScanner(ISimpleTextScannerCallback callback) {

		this.callback = callback;
		this.matchers = new ArrayList<>();
		this.normalText = new StringBuilder();
		this.whitespace = new StringBuilder();
	}

	public void addMatcher(ISimpleTextMatcher matcher) {

		matchers.add(matcher);
	}

	public void clearMatchers() {

		matchers.clear();
	}

	public void scan(String text) {

		this.text = text;
		this.index = 0;

		while (index < text.length()) {
			if (isMatch()) {
				outputNormalTextIfAny();
				outputWhitespaceTextIfAny();
				currentMatcher.consumeMatchingText(text.substring(index, index + matchingLength));
				this.index += matchingLength;
			} else if (isWhiteSpace()) {
				outputNormalTextIfAny();
				whitespace.append(text.charAt(index));
				this.index += 1;
			} else {
				outputWhitespaceTextIfAny();
				normalText.append(text.charAt(index));
				this.index += 1;
			}
		}
		outputNormalTextIfAny();
		outputWhitespaceTextIfAny();
	}

	private boolean isWhiteSpace() {

		return Character.isWhitespace(text.charAt(index));
	}

	private boolean isMatch() {

		String subString = text.substring(index);
		for (ISimpleTextMatcher matcher: matchers) {
			int matchingLength = matcher.getMatchingLength(subString);
			if (matchingLength > 0) {
				this.currentMatcher = matcher;
				this.matchingLength = matchingLength;
				return true;
			}
		}
		this.currentMatcher = null;
		this.matchingLength = 0;
		return false;
	}

	private void outputNormalTextIfAny() {

		if (normalText.length() > 0) {
			callback.consumeNormalText(normalText.toString());
			this.normalText.setLength(0);
		}
	}

	private void outputWhitespaceTextIfAny() {

		if (whitespace.length() > 0) {
			callback.consumeWhitespace(whitespace.toString());
			this.whitespace.setLength(0);
		}
	}
}
