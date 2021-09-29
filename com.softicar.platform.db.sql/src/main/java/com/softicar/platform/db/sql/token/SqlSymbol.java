package com.softicar.platform.db.sql.token;

public enum SqlSymbol implements ISqlToken {
	// symbols
	COMMA(","),
	DOT("."),
	LEFT_PARENTHESIS("("),
	RIGHT_PARENTHESIS(")"),

	// bit manipulation
	BIT_OR("|"),
	BIT_AND("&"),
	BIT_XOR("^"),
	BIT_INVERSION("~"),

	// arithmetic
	MINUS("-"),
	PLUS("+"),
	MULTIPLY("*"),
	DIVIDE("/"),
	MODULO("%"),

	// comparators
	EQUAL("="),
	GREATER(">"),
	GREATER_EQUAL(">="),
	LESS("<"),
	LESS_EQUAL("<="),
	LESS_GREATER("<>"),
	NOT_EQUAL("!=");

	private static final SqlSymbolMap map = new SqlSymbolMap();
	private final String text;

	private SqlSymbol(String text) {

		this.text = text;
	}

	public static boolean isSymbol(String text) {

		return map.get(text) != null;
	}

	public static SqlSymbol getByText(String text) {

		SqlSymbol symbol = map.get(text);
		if (symbol != null) {
			return symbol;
		} else {
			throw new IllegalArgumentException(String.format("No SQL symbol '%s' found.", text));
		}
	}

	@Override
	public String toString() {

		return text;
	}

	public String getText() {

		return text;
	}
}
