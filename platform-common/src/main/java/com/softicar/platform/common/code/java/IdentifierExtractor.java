package com.softicar.platform.common.code.java;

import java.util.Collection;

/**
 * Extracts the identifiers from a given line.
 * 
 * @author Oliver Richers
 */
public class IdentifierExtractor {

	private int index;
	private final String line;
	private final Collection<String> identifiers;

	public IdentifierExtractor(String line, Collection<String> identifiers) {

		this.index = 0;
		this.line = line;
		this.identifiers = identifiers;
	}

	public void extract() {

		while (index < line.length()) {
			if (findStartOfIdentifyer()) {
				readIdentifier();
			}
		}
	}

	private boolean findStartOfIdentifyer() {

		for (; index < line.length(); ++index) {
			if (isStartOfIdentifyer()) {
				return true;
			}
		}
		return false;
	}

	private boolean isStartOfIdentifyer() {

		char c = line.charAt(index);
		return Character.isJavaIdentifierStart(c);
	}

	private void readIdentifier() {

		String identifier = new IdentifierReader(line.substring(index)).read();
		index += identifier.length();
		identifiers.add(identifier);
	}
}
