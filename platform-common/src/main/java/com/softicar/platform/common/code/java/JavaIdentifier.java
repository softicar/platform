package com.softicar.platform.common.code.java;

import java.util.List;

/**
 * Represents a Java identifier.
 * 
 * @author Oliver Richers
 */
public class JavaIdentifier {

	private final List<WordFragment> fragments;

	public JavaIdentifier(String name) {

		this.fragments = WordFragment.parse(name);
	}

	public JavaIdentifier(List<WordFragment> fragments) {

		this.fragments = fragments;
	}

	public String asField() {

		return IdentifierNames.Type.LOWER_CAMEL.get(fragments);
	}

	public String asParameter() {

		return IdentifierNames.Type.LOWER_CAMEL.get(fragments);
	}

	public String asConstant() {

		return IdentifierNames.Type.UPPER_WITH_UNDER_SCORE.get(fragments);
	}

	public String asClass() {

		return IdentifierNames.Type.UPPER_CAMEL.get(fragments);
	}

	public String asSetter() {

		return "set" + IdentifierNames.Type.UPPER_CAMEL.get(fragments);
	}

	public String asGetter() {

		return "get" + IdentifierNames.Type.UPPER_CAMEL.get(fragments);
	}
}
