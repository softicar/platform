package com.softicar.platform.db.core.sql.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Tokenizer for SQL statements.
 * <p>
 * This class breaks a given SQL text down into distinct tokens.
 *
 * @author Oliver Richers
 */
public class DbSqlTokenizer {

	private final String sql;
	private boolean skipWhiteSpace;
	private List<DbSqlToken> tokens;
	private StringBuilder currentToken;
	private DbSqlTokenType currentTokenType;
	private int index;
	private InternalTokenCharacter stringDelimiter;
	private InternalTokenCharacter character;
	private InternalTokenCharacter previousCharacter;

	/**
	 * Creates the tokenizer with the given SQL text.
	 *
	 * @param sql
	 *            the SQL text (never null)
	 */
	public DbSqlTokenizer(String sql) {

		this.sql = sql;
		this.skipWhiteSpace = true;
	}

	public DbSqlTokenizer setSkipWhiteSpace(boolean skipWhiteSpace) {

		this.skipWhiteSpace = skipWhiteSpace;
		return this;
	}

	/**
	 * Breaks down the given statement into tokens.
	 *
	 * @return list of tokens
	 */
	public List<DbSqlToken> tokenize() {

		this.tokens = new ArrayList<>();
		this.currentToken = new StringBuilder();
		this.currentTokenType = null;

		for (this.index = 0; index != sql.length(); ++index) {
			this.previousCharacter = character;
			this.character = new InternalTokenCharacter(sql.charAt(index));

			if (currentTokenType == null) {
				scanNewToken();
			} else {
				switch (currentTokenType) {
				case IDENTIFIER:
					scanIdentifier();
					break;
				case NUMBER:
					scanNumber();
					break;
				case STRING:
					scanString();
					break;
				case WHITESPACE:
					scanWhitespace();
					break;
				case ILLEGAL:
				case SYMBOL:
					scanNewToken();
					break;
				}
			}
		}
		finishCurrentToken();
		return tokens;
	}

	// ------------------------------ new token ------------------------------ //

	private void scanNewToken() {

		if (character.isIdentifierStart()) {
			startNewToken(DbSqlTokenType.IDENTIFIER);
		} else if (character.isDigit()) {
			startNewToken(DbSqlTokenType.NUMBER);
		} else if (character.isStringDelimiter()) {
			startStringToken();
		} else if (character.isSymbol()) {
			addCharacterAsSymbolToken();
		} else if (character.isWhitespace()) {
			startNewToken(DbSqlTokenType.WHITESPACE);
		} else {
			addCharacterAsIllegalToken();
		}
	}

	private void startNewToken(DbSqlTokenType tokenType) {

		this.currentTokenType = tokenType;
		appendCharacterToCurrentToken();
	}

	// ------------------------------ identifier ------------------------------ //

	private void scanIdentifier() {

		if (character.isIdentifierPart()) {
			appendCharacterToCurrentToken();
		} else {
			finishCurrentToken();
			scanNewToken();
		}
	}

	// ------------------------------ number ------------------------------ //

	private void scanNumber() {

		if (isNumberPart() || isStartOfNumberExponent() || isExponentSign()) {
			appendCharacterToCurrentToken();
		} else {
			finishCurrentToken();
			scanNewToken();
		}
	}

	private boolean isNumberPart() {

		return character.isDigit() || character.is('.');
	}

	private boolean isStartOfNumberExponent() {

		if (character.isIn('e', 'E') && hasNextCharacter()) {
			InternalTokenCharacter nextCharacter = getNextCharacter();
			return nextCharacter.isDigit() || nextCharacter.isIn('+', '-');
		} else {
			return false;
		}
	}

	private boolean isExponentSign() {

		return character.isIn('+', '-') && previousCharacter.isIn('e', 'E');
	}

	// ------------------------------ string ------------------------------ //

	private void startStringToken() {

		addCharacterAsSymbolToken();

		this.stringDelimiter = character;
		this.currentTokenType = DbSqlTokenType.STRING;
	}

	private void scanString() {

		if (character.equals(stringDelimiter)) {
			if (hasNextCharacter() && getNextCharacter().equals(stringDelimiter)) {
				appendCharacterToCurrentToken();
				++index;
			} else {
				finishStringToken();
			}
		} else if (character.isBackslash() && hasNextCharacter()) {
			currentToken.append(getNextCharacter().decodeBackslashSequenceCharacter());
			++index;
		} else {
			appendCharacterToCurrentToken();
		}
	}

	private void finishStringToken() {

		finishCurrentToken();
		addCharacterAsSymbolToken();
	}

	// ------------------------------ whitespace ------------------------------ //

	private void scanWhitespace() {

		if (character.isWhitespace()) {
			appendCharacterToCurrentToken();
		} else {
			finishCurrentToken();
			scanNewToken();
		}
	}

	// ------------------------------ next character ------------------------------ //

	private boolean hasNextCharacter() {

		return index + 1 < sql.length();
	}

	private InternalTokenCharacter getNextCharacter() {

		return new InternalTokenCharacter(sql.charAt(index + 1));
	}

	// ------------------------------ character tokens ------------------------------ //

	private void addCharacterAsSymbolToken() {

		tokens.add(new DbSqlToken(DbSqlTokenType.SYMBOL, "" + character));
	}

	private void addCharacterAsIllegalToken() {

		tokens.add(new DbSqlToken(DbSqlTokenType.ILLEGAL, "" + character));
	}

	// ------------------------------ current token ------------------------------ //

	private void appendCharacterToCurrentToken() {

		currentToken.append(character);
	}

	private void finishCurrentToken() {

		if (currentToken.length() > 0 || currentTokenType == DbSqlTokenType.STRING) {
			if (currentTokenType == DbSqlTokenType.WHITESPACE && skipWhiteSpace) {
				// skipping whitespace
			} else {
				tokens.add(new DbSqlToken(currentTokenType, currentToken.toString()));
			}
			currentToken.setLength(0);
		}

		this.stringDelimiter = null;
		this.currentTokenType = null;
	}
}
