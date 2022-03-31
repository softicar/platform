package com.softicar.platform.emf.editor;

public enum EmfAttributeValueMode {

	DISPLAY,
	HIDDEN,
	MANDATORY_INPUT,
	OPTIONAL_INPUT;

	public boolean isDisplay() {

		return this == DISPLAY;
	}

	public boolean isHidden() {

		return this == HIDDEN;
	}

	public boolean isInput() {

		return this == MANDATORY_INPUT || this == OPTIONAL_INPUT;
	}

	public boolean isMandatory() {

		return this == MANDATORY_INPUT;
	}
}
