package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;

public class AutoCompleteWithChangeHandlerTestCase extends AbstractTestCaseDiv {

	private final AutoCompleteTestCaseInput input;

	public AutoCompleteWithChangeHandlerTestCase() {

		appendChild(new DomDiv()).appendText("Auto complete with change handler:");

		this.input = new AutoCompleteTestCaseInput(this).listenToChange();
		appendChild(input);

		appendNewChild(DomElementTag.BR);
		appendChild(new DomActionBar(new MarkAsInvalidButton()));
	}

	private class MarkAsInvalidButton extends DomButton {

		public MarkAsInvalidButton() {

			setLabel(IDisplayString.create("mark as invalid"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			getDomEngine().setAutoCompleteInputInvalid(input);
		}
	}
}
