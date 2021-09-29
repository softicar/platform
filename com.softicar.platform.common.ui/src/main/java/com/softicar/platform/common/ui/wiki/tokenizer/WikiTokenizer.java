package com.softicar.platform.common.ui.wiki.tokenizer;

import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import com.softicar.platform.common.ui.wiki.token.matcher.IWikiTokenMatcher;
import com.softicar.platform.common.ui.wiki.token.matcher.MainWikiTokenMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class WikiTokenizer implements IWikiTokenizer {

	private final String wikiText;
	private final Stack<IWikiTokenMatcher> matcherStack;
	private final List<WikiToken> tokens;
	private StringBuilder plainText;
	private int index;

	public WikiTokenizer(String wikiText) {

		this.wikiText = wikiText;
		this.matcherStack = new Stack<>();
		this.matcherStack.push(new MainWikiTokenMatcher());
		this.tokens = new ArrayList<>();
		this.plainText = new StringBuilder();
		this.index = 0;
	}

	public List<WikiToken> tokenize() {

		int length = wikiText.length();
		while (index < length) {
			if (matcherStack.peek().match(this)) {
				// okay
			} else {
				addPlainText(wikiText.charAt(index));
			}
		}
		flushPlainText();

		return tokens;
	}

	@Override
	public int getLength() {

		return wikiText.length() - index;
	}

	@Override
	public boolean startsWith(String pattern) {

		return wikiText.startsWith(pattern, index);
	}

	@Override
	public Optional<Integer> findIndexOf(String pattern) {

		for (int i = index; i < wikiText.length(); i++) {
			if (wikiText.startsWith(pattern, i)) {
				return Optional.of(i - index);
			}
		}
		return Optional.empty();
	}

	@Override
	public int findIndexOf(char character, int offset) {

		for (int i = index + offset; i < wikiText.length(); i++) {
			if (wikiText.charAt(i) == character) {
				return i - index;
			}
		}
		return -1;
	}

	@Override
	public int findIndexOfOther(char character, int offset) {

		for (int i = index + offset; i < wikiText.length(); i++) {
			if (wikiText.charAt(i) != character) {
				return i - index;
			}
		}
		return wikiText.length() - index;
	}

	@Override
	public String getSubstring(int beginOffset, int endOffset) {

		return wikiText.substring(index + beginOffset, index + endOffset);
	}

	@Override
	public char getCharAt(int offset, char fallback) {

		int i = index + offset;
		return i >= 0 && i < wikiText.length()? wikiText.charAt(i) : fallback;
	}

	@Override
	public void addToken(WikiToken token) {

		flushPlainText();
		tokens.add(token);
		index += token.getConsumedText().length();
	}

	private void addPlainText(char character) {

		plainText.append(character);
		index += 1;
	}

	private void flushPlainText() {

		if (plainText.length() > 0) {
			tokens.add(new WikiToken(WikiTokenType.TEXT, plainText.toString()));
			plainText = new StringBuilder();
		}
	}
}
