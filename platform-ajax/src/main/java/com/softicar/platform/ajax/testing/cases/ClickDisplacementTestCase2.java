package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;

public class ClickDisplacementTestCase2 extends AbstractTestCaseDiv {

	public ClickDisplacementTestCase2() {

		appendChild(new DomActionBar(new PromptButton()));
	}

	private class PromptButton extends DomButton {

		public PromptButton() {

			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("Click to get a prompt."));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executePrompt(//
				text -> log("got input: '%s'", text),
				IDisplayString.create("Please enter something and press ENTER."),
				"");
		}
	}
}
