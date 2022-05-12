package com.softicar.platform.ajax.engine;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.writer.IManagedWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class filters JavaScript code and removes unnecessary whitespace and
 * comments.
 *
 * @author Oliver Richers
 */
// FIXME this class needs a cleanup
public class JavascriptStripper {

	// @formatter:off
	public static final String[] OPERATORS = {
		"{", "}", "(", ")", "[", "]",
		"==", ">=", "<=", "<", ">",
		"=", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "~=", "!=", ">>=", "<<=",
		"+", "-", "*", "/", "%", "&", "|", "~", "!", ">>", "<<", ".", ":", "?", "++", "--",
		";", ",", "//", "/*", "*/", "'", "\"", "`"
	};
	// @formatter:on

	public JavascriptStripper(Reader reader, IManagedWriter writer) {

		m_reader = reader;
		m_writer = writer;
	}

	public void transfer() {

		Token token;
		Token previousToken = null;
		while ((token = getNextToken()) != null) {
			switch (token.getTokenType()) {
			case OPERATOR:
				if (token.getText().equals("/*")) {
					skipBlockComment();
					token = previousToken;
				} else if (token.getText().equals("//")) {
					skipLineComment();
					token = previousToken;
				} else if (token.getText().equals("'") || token.getText().equals("`") || token.getText().equals("\"")) {
					m_writer.write(token.getText());
					copyStringLiteral(token);
				} else {
					m_writer.write(token.getText());
				}
				break;
			default:
				if (previousToken != null && !previousToken.getTokenType().equals(TokenType.OPERATOR)) {
					m_writer.write(' ');
				}
				m_writer.write(token.getText());
				break;
			}

			previousToken = token;
		}
	}

	private Token getNextToken() {

		while (true) {
			String tmp = getChars(3);
			if (tmp.length() == 0) {
				return null;
			}

			char c = tmp.charAt(0);
			if (Character.isWhitespace(c)) {
				consumeChars(1);
				continue;
			} else if (Character.isJavaIdentifierStart(c)) {
				return readWordToken();
			} else if (Character.isDigit(c)) {
				return readNumberToken();
			} else if (tmp.length() >= 3 && m_operatorSet.contains(tmp)) {
				consumeChars(3);
				return new Token(TokenType.OPERATOR, tmp);
			} else if (tmp.length() >= 2 && m_operatorSet.contains(tmp.substring(0, 2))) {
				consumeChars(2);
				return new Token(TokenType.OPERATOR, tmp.substring(0, 2));
			} else if (tmp.length() >= 1 && m_operatorSet.contains(tmp.substring(0, 1))) {
				consumeChars(1);
				return new Token(TokenType.OPERATOR, tmp.substring(0, 1));
			} else {
				throw new SofticarException("Invalid data '%s'.", tmp);
			}
		}
	}

	private Token readWordToken() {

		StringBuilder builder = new StringBuilder();
		builder.append(getChars(1));
		consumeChars(1);
		while (true) {
			String tmp = getChars(1);
			if (tmp.length() != 1) {
				break;
			}
			if (!Character.isJavaIdentifierPart(tmp.charAt(0))) {
				break;
			}
			builder.append(tmp);
			consumeChars(1);
		}
		return new Token(TokenType.WORD, builder.toString());
	}

	private Token readNumberToken() {

		StringBuilder builder = new StringBuilder();
		builder.append(getChars(1));
		consumeChars(1);
		while (true) {
			String tmp = getChars(1);
			if (tmp.length() != 1) {
				break;
			}
			char c = tmp.charAt(0);
			if (!Character.isLetterOrDigit(c) && c != '.') {
				break;
			}
			builder.append(tmp);
			consumeChars(1);
		}
		return new Token(TokenType.NUMBER, builder.toString());
	}

	private String getChars(int count) {

		try {
			if (count > m_buffer.length) {
				throw new SofticarException("Buffer overflow.");
			}

			// need to fetch more chars?
			if (m_bufferLength < count && !m_eof) {
				int n = count - m_bufferLength;
				for (int i = 0; i < n; ++i) {
					int tmp = m_reader.read();
					if (tmp == -1) {
						break;
					}
					m_buffer[m_bufferLength++] = (char) tmp;
				}

				// check for end of file
				m_eof = m_bufferLength < count;
			}

			int n = Math.min(count, m_bufferLength);
			return new String(m_buffer, 0, n);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void consumeChars(int count) {

		if (count > m_bufferLength) {
			throw new SofticarException("Buffer overflow.");
		}

		// reduce the logical buffer length
		m_bufferLength -= count;

		// move remaining chars to the front
		for (int i = 0; i < m_bufferLength; ++i) {
			m_buffer[i] = m_buffer[count + i];
		}
	}

	private void copyStringLiteral(Token token) {

		boolean backslash = false;
		while (true) {
			String tmp = getChars(1);
			if (tmp.length() != 1) {
				throw new SofticarException("Unexpected end of file while reading line comment.");
			}

			consumeChars(1);
			m_writer.write(tmp);

			if (backslash) {
				backslash = false;
			} else if (tmp.equals(token.getText())) {
				break;
			}
		}
	}

	private void skipLineComment() {

		while (true) {
			String tmp = getChars(1);
			if (tmp.length() != 1) {
				throw new SofticarException("Unexpected end of file while reading line comment.");
			} else if (tmp.charAt(0) == '\n') {
				consumeChars(1);
				break;
			} else {
				consumeChars(1);
			}
		}
	}

	private void skipBlockComment() {

		while (true) {
			String tmp = getChars(2);
			if (tmp.length() != 2) {
				throw new SofticarException("Unexpected end of file while reading block comment.");
			} else if (tmp.equals("*/")) {
				consumeChars(2);
				break;
			} else {
				consumeChars(1);
			}
		}
	}

	private static enum TokenType {
		OPERATOR,
		WORD,
		NUMBER;
	}

	private static class Token {

		public Token(TokenType type, String text) {

			m_tokenType = type;
			m_text = text;
		}

		@Override
		public String toString() {

			return m_tokenType + " '" + m_text + "'";
		}

		public TokenType getTokenType() {

			return m_tokenType;
		}

		public String getText() {

			return m_text;
		}

		private final TokenType m_tokenType;
		private final String m_text;
	}

	private boolean m_eof = false;
	private int m_bufferLength = 0;
	private final char[] m_buffer = new char[16];
	private final Reader m_reader;
	private final IManagedWriter m_writer;

	private static Set<String> m_operatorSet = new TreeSet<>(Arrays.asList(OPERATORS));
}
