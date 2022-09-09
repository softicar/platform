package com.softicar.platform.dom.event;

/**
 * Represents a modifier on a keyboard.
 *
 * @author Alexander Schmidt
 */
public enum DomModifier {

	ALT("Alt"),
	CONTROL("Control"),
	META("Meta"),
	SHIFT("Shift");

	private final String javascriptName;

	private DomModifier(String javascriptName) {

		this.javascriptName = javascriptName;
	}

	@Override
	public String toString() {

		return javascriptName;
	}
}
