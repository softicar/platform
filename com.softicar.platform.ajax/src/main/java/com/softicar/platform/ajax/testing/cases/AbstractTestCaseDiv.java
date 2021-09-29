package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.elements.DomFieldset;

public abstract class AbstractTestCaseDiv extends DomFieldset {

	private final LogDiv logDiv;

	public AbstractTestCaseDiv() {

		this.logDiv = new LogDiv();
	}

	public void log(String text, Object...arguments) {

		if (logDiv.getParent() == null) {
			appendChild(logDiv);
		}
		logDiv.log(text, arguments);
	}
}
