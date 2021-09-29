package com.softicar.platform.common.code.java;

import java.util.TreeSet;

/**
 * Searches through the list of code lines for identifiers
 * 
 * @author Oliver Richers
 */
public class CodeAnalyser {

	private final TreeSet<String> identifiers = new TreeSet<>();

	public TreeSet<String> getIdentifiers() {

		return identifiers;
	}

	public void analyse(Iterable<String> code) {

		for (String line: code) {
			new IdentifierExtractor(line, identifiers).extract();
		}
	}
}
