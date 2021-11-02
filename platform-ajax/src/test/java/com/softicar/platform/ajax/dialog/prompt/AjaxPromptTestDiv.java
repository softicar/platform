package com.softicar.platform.ajax.dialog.prompt;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;

class AjaxPromptTestDiv extends DomDiv {

	protected static final String QUESTION = "Give some input:";
	protected static final String DEFAULT_INPUT = "DEFAULT";
	private final TestButton testButton;
	private final DomDiv outputDiv;

	public AjaxPromptTestDiv() {

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

		executePrompt(outputDiv::appendText, IDisplayString.create(QUESTION), DEFAULT_INPUT);
	}

	private class TestButton extends DomButton {

		public TestButton() {

			setLabel(IDisplayString.create("Click to Prompt"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			click();
		}
	}
}
