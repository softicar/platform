package com.softicar.platform.common.code.java;

public class IdentifierReader {

	private int index;
	private final String line;
	private final StringBuilder identifier;

	public IdentifierReader(String line) {

		this.index = 0;
		this.line = line;
		this.identifier = new StringBuilder();
	}

	public String read() {

		readFirstChar();
		readUntilEndOfIdentifier();
		return identifier.toString();
	}

	private void readFirstChar() {

		identifier.append(line.charAt(index));
		++index;
	}

	private void readUntilEndOfIdentifier() {

		while (index < line.length() && isIdentifierPart()) {
			appendChar();
		}
	}

	private void appendChar() {

		char c = line.charAt(index++);
		identifier.append(c);
	}

	private boolean isIdentifierPart() {

		char c = line.charAt(index);
		return Character.isJavaIdentifierPart(c);
	}
}
