package com.softicar.platform.ajax.dialog.confirm;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;

class AjaxConfirmTestDiv extends DomDiv {

	protected static final String QUESTION = "Are you sure?";
	protected static final String CONFIRMED = "CONFIRMED";
	private final TestButton testButton;
	private final DomDiv outputDiv;

	public AjaxConfirmTestDiv() {

		this.testButton = appendChild(new TestButton());
		this.outputDiv = appendChild(new DomDiv());
	}

	public DomButton getTestButton() {

		return testButton;
	}

	public DomDiv getOutputDiv() {

		return outputDiv;
	}

	private void click() {

		executeConfirm(() -> outputDiv.appendText(CONFIRMED), IDisplayString.create(QUESTION));
	}

	private class TestButton extends DomButton {

		public TestButton() {

			setLabel(IDisplayString.create("Click to Confirm"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			click();
		}
	}
}
