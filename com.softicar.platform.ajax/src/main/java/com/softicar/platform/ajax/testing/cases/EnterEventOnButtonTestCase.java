package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.ajax.image.AjaxImages;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomTextInput;

public class EnterEventOnButtonTestCase extends AbstractTestCaseDiv {

	public EnterEventOnButtonTestCase() {

		appendText("Pressing ENTER in the input should be caught by the parent div.");
		appendNewChild(DomElementTag.BR);
		appendText("Pressing ENTER on the button should be caught by the button.");
		appendNewChild(DomElementTag.BR);
		appendChild(new TestDiv());
	}

	private class TestDiv extends DomDiv implements IDomEnterKeyEventHandler {

		public TestDiv() {

			appendChild(new DomTextInput());
			appendChild(new DomActionBar(new TextButton(), new IconAndTextButton(), new IconButton()));
		}

		@Override
		public void handleEnterKey(IDomEvent event) {

			log("pressed ENTER on parent div");
		}
	}

	private class TextButton extends DomButton {

		public TextButton() {

			setLabel(IDisplayString.create("Press enter on me."));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			log("SUBMITTED text button");
		}
	}

	private class IconButton extends DomButton {

		public IconButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			log("SUBMITTED icon button");
		}
	}

	private class IconAndTextButton extends DomButton {

		public IconAndTextButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("Press enter on me."));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			log("SUBMITTED icon and text button");
		}
	}
}
