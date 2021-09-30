package com.softicar.platform.db.core.sql.parser;

import com.softicar.platform.common.core.exceptions.SofticarException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Scanner for SQL statements.
 * <p>
 * This class can be used to iterate over the tokens of an SQL text.
 *
 * @author Oliver Richers
 */
public class DbSqlScanner {

	private final List<DbSqlToken> tokens;
	private int currentTokenIndex;

	/**
	 * Creates this scanner for the given SQL statement.
	 * <p>
	 * The token index will point to the first token of the statement.
	 *
	 * @param sql
	 *            the SQL text (never null)
	 */
	public DbSqlScanner(String sql) {

		this.tokens = new DbSqlTokenizer(sql).tokenize();
		this.currentTokenIndex = 0;
	}

	/**
	 * Returns all tokens that have been scanned successfully.
	 * <p>
	 * The current token is not included in the list.
	 *
	 * @return all scanned tokens (never null)
	 */
	public List<String> getScannedTokens() {

		return tokens//
			.subList(0, currentTokenIndex)
			.stream()
			.map(DbSqlToken::getText)
			.collect(Collectors.toList());
	}

	/**
	 * Returns whether more tokens are available.
	 *
	 * @return <i>true</i> if more tokens are available; <i>false</i> otherwise
	 */
	public boolean hasMoreTokens() {

		return currentTokenIndex < tokens.size();
	}

	/**
	 * Tests if the current token matches any of the given tokens.
	 *
	 * @param tokens
	 *            the tokens to test for (never null)
	 * @return <i>true</i> if the current token matches one of the given tokens;
	 *         <i>false</i> otherwise
	 */
	public boolean testCurrentTokenAnyOf(String...tokens) {

		for (String token: tokens) {
			if (testCurrentToken(token)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests if the current token matches the given token.
	 *
	 * @param token
	 *            the token to test for (never null)
	 * @return <i>true</i> if the current token matches the given token;
	 *         <i>false</i> otherwise
	 */
	public boolean testCurrentToken(String token) {

		if (currentTokenIndex < tokens.size()) {
			return tokens.get(currentTokenIndex).getText().equalsIgnoreCase(token);
		} else {
			return false;
		}
	}

	/**
	 * Tests if the current token matches the given token.
	 * <p>
	 * If the current matches the given token, the token index is advanced by
	 * one.
	 *
	 * @param token
	 *            the token to test for (never null)
	 * @return <i>true</i> if the current token matches the given token;
	 *         <i>false</i> otherwise
	 */
	public boolean testAndConsumeTokenIfMatched(String token) {

		if (testCurrentToken(token)) {
			consumeCurrentToken();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the current token and advances the token index.
	 *
	 * @return the current token (never null)
	 */
	public String consumeCurrentToken() {

		String token = peekCurrentToken();
		++currentTokenIndex;
		return token;
	}

	/**
	 * Returns the current token.
	 * <p>
	 * The current token index will <b>not</b> be changed.
	 *
	 * @return the current token (never null)
	 */
	public String peekCurrentToken() {

		return tokens.get(currentTokenIndex).getText();
	}

	/**
	 * Checks that the current token matches the given token and advances the
	 * token index.
	 *
	 * @param token
	 *            the token to check for (never null)
	 * @throws SofticarException
	 *             if the current token does not match the given token
	 */
	public void assertToken(String token) {

		String currentToken = consumeCurrentToken();
		if (!currentToken.equalsIgnoreCase(token)) {
			throw new SofticarException(//
				"Expected token '%s' but got '%s'. Parsed tokens: %s",
				token,
				currentToken,
				tokens.subList(0, currentTokenIndex).toString());
		}
	}

	/**
	 * Checks that all tokens starting from the current token match the given
	 * sequence of tokens and advances the token index.
	 *
	 * @param tokens
	 *            the tokens to check for (never null)
	 * @throws SofticarException
	 *             if one or more of the current tokens does not match the given
	 *             tokens
	 */
	public void assertTokens(String...tokens) {

		for (String token: tokens) {
			assertToken(token);
		}
	}

	/**
	 * Checks that the current token matches one of the given tokens and
	 * advances the token index.
	 *
	 * @param tokens
	 *            the tokens to check for (never null)
	 * @return the current token (never null)
	 * @throws SofticarException
	 *             if the current token does not match any of the given token
	 */
	public String assertAnyToken(String...tokens) {

		String currentToken = consumeCurrentToken();
		for (String token: tokens) {
			if (token.equalsIgnoreCase(currentToken)) {
				return currentToken;
			}
		}
		throw new SofticarException(//
			"Expected one token of %s but got '%s'. Parsed tokens: %s",
			Arrays.asList(tokens),
			currentToken,
			this.tokens.subList(0, currentTokenIndex).toString());
	}

	/**
	 * Returns the token surrounded by a pair of the given token.
	 * <p>
	 * This method advances the token index by three tokens.
	 *
	 * @param token
	 *            the surrounding token (never null)
	 * @return the token in-between (never null)
	 */
	public String getTokenBetween(String token) {

		return getTokenBetween(token, token);
	}

	/**
	 * Returns the token surrounded by the given tokens.
	 * <p>
	 * This method advances the token index by three tokens.
	 *
	 * @param before
	 *            the preceding token (never null)
	 * @param after
	 *            the succeeding token (never null)
	 * @return the token in-between (never null)
	 */
	public String getTokenBetween(String before, String after) {

		assertToken(before);
		String token = consumeCurrentToken();
		assertToken(after);
		return token;
	}

	/**
	 * Returns the token surrounded quotes.
	 * <p>
	 * This method advances the token index by three tokens.
	 *
	 * @return the token between the quotes (never null)
	 */
	public String getQuotedToken() {

		assertAnyToken("`", "'");
		String token = consumeCurrentToken();
		assertAnyToken("`", "'");
		return token;
	}

	/**
	 * Returns the tokens from a comma-separated list of quoted tokens
	 * surrounded by parentheses.
	 * <p>
	 * This method is especially useful for scanning a list of SQL columns, e.g.
	 * the column list of an INSERT statement.
	 * <p>
	 * This method advances the token index beyond the closing parenthesis.
	 *
	 * @return the quoted tokens (never null)
	 */
	public List<String> getListOfQuotedTokens() {

		List<String> tokens = new ArrayList<>();

		assertToken("(");
		while (true) {
			// check for empty block
			if (testCurrentToken(")")) {
				break;
			}

			// get quoted token
			tokens.add(getQuotedToken());

			// end of list or next token
			if (testCurrentToken(")")) {
				break;
			} else {
				assertToken(",");
			}
		}
		assertToken(")");

		return tokens;
	}
}
