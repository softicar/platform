package com.softicar.platform.common.string.csv;

class CsvDelimiter {

	private final CsvDelimiter.Type type;
	private final int index;
	private final int length;

	public static CsvDelimiter comma(int index) {

		return new CsvDelimiter(Type.COMMA, index, 1);
	}

	public static CsvDelimiter newline(int index) {

		return new CsvDelimiter(Type.NEWLINE, index, 1);
	}

	public static CsvDelimiter none() {

		return new CsvDelimiter(Type.NONE, -1, 0);
	}

	public static CsvDelimiter quote(int index) {

		return quote(index, 1);
	}

	public static CsvDelimiter quote(int index, int length) {

		return new CsvDelimiter(Type.QUOTE, index, length);
	}

	private CsvDelimiter(CsvDelimiter.Type type, int index, int length) {

		this.type = type;
		this.index = index;
		this.length = length;
	}

	public boolean isComma() {

		return type == Type.COMMA;
	}

	public boolean isNewline() {

		return type == Type.NEWLINE;
	}

	public boolean isNone() {

		return type == Type.NONE;
	}

	public boolean isQuote() {

		return type == Type.QUOTE;
	}

	public int getIndex() {

		return index;
	}

	public int getLength() {

		return length;
	}

	private static enum Type {

		COMMA,
		NEWLINE,
		NONE,
		QUOTE;
	}
}
