package com.softicar.platform.db.core.sql.parser;

import java.util.Objects;

class InternalTokenCharacter {

	private final char character;

	public InternalTokenCharacter(char character) {

		this.character = character;
	}

	public char getCharacter() {

		return character;
	}

	public boolean is(char character) {

		return this.character == character;
	}

	public boolean isIn(char...characters) {

		for (char character: characters) {
			if (this.character == character) {
				return true;
			}
		}
		return false;
	}

	public boolean isStringDelimiter() {

		return isIn('`', '"', '\'');
	}

	public boolean isNull() {

		return character == 0;
	}

	public boolean isSymbol() {

		return isIn('(', ')', '.', ',', '=', '+', '-', '*', '/', '&', '|', '~', '^', '!', ';');
	}

	public boolean isIdentifierStart() {

		return Character.isAlphabetic(character);
	}

	public boolean isIdentifierPart() {

		return Character.isAlphabetic(character) || Character.isDigit(character) || isIn('_', '$');
	}

	public boolean isWhitespace() {

		return Character.isWhitespace(character);
	}

	public boolean isDigit() {

		return Character.isDigit(character);
	}

	public boolean isBackslash() {

		return character == '\\';
	}

	public boolean isBackslashSequenceCharacter() {

		return InternalBackslashSequenceCharacterMap.getInstance().contains(character);
	}

	public char decodeBackslashSequenceCharacter() {

		return InternalBackslashSequenceCharacterMap.getInstance().get(character);
	}

	@Override
	public String toString() {

		return character + "";
	}

	@Override
	public int hashCode() {

		return Objects.hash(character);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof InternalTokenCharacter) {
			return ((InternalTokenCharacter) object).character == character;
		} else {
			return false;
		}
	}
}
