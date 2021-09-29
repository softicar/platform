package com.softicar.platform.common.code.tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * A very simple tokenizer recognizing white-space and quoted strings.
 *
 * @author Oliver Richers
 */
public class SimpleTokenizer {

	private final CharSequence charSequence;
	private List<String> tokens;
	private StringBuilder token;
	private int index;
	private boolean escaped;
	private Character quotingChar;

	public SimpleTokenizer(CharSequence charSequence) {

		this.charSequence = charSequence;
	}

	public List<String> getTokens() {

		this.quotingChar = null;
		this.escaped = false;
		this.tokens = new ArrayList<>();
		this.token = new StringBuilder();
		this.index = 0;
		while (index < charSequence.length()) {
			char c = charSequence.charAt(index);
			if (isInQuotes()) {
				processQuotedChar(c);
			} else {
				processNormalChar(c);
			}
			index++;
		}
		finishToken();
		return tokens;
	}

	private boolean isInQuotes() {

		return quotingChar != null;
	}

	private void processNormalChar(char c) {

		if (Character.isWhitespace(c)) {
			finishToken();
		} else if (isQuotingChar(c)) {
			finishToken();
			quotingChar = c;
			escaped = false;
		} else {
			token.append(c);
		}
	}

	private boolean isQuotingChar(char c) {

		return c == '\'' || c == '"';
	}

	private void processQuotedChar(char c) {

		if (c == '\\' && !escaped) {
			escaped = true;
		} else if (c == quotingChar && !escaped) {
			finishToken();
			quotingChar = null;
		} else {
			token.append(c);
			escaped = false;
		}
	}

	private void finishToken() {

		if (token.length() > 0 || isInQuotes()) {
			tokens.add(token.toString());
			token.setLength(0);
		}
	}
}
