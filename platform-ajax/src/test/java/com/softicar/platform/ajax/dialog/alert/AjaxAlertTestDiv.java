package com.softicar.platform.ajax.dialog.alert;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;

class AjaxAlertTestDiv extends DomDiv {

	protected static final String TEXT = "Some Alert Text";
	private final TestButton testButton;

	public AjaxAlertTestDiv() {

		this.testButton = appendChild(new TestButton());
	}

	public DomButton getTestButton() {

		return testButton;
	}

	private void click() {

		executeAlert(IDisplayString.create(TEXT));
	}

	private class TestButton extends DomButton {

		public TestButton() {

			setLabel(IDisplayString.create("Click to spawn an alert"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			click();
		}
	}
}
