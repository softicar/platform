package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;

public class AutoCompleteTestCase extends AbstractTestCaseDiv {

	private final AutoCompleteTestCaseInput input;

	public AutoCompleteTestCase() {

		appendChild(new DomDiv()).appendText("Auto complete without change handler:");

		this.input = appendChild(new AutoCompleteTestCaseInput(this));

		appendChild(
			new DomActionBar(//
				new SetValueButton()));
		appendChild(
			new DomActionBar(//
				new GetSelectionValueButton(),
				new GetValueStringOrNullButton(),
				new GetValueStringOrEmptyButton(),
				new GetRawValueStringButton(),
				new FireAssertionButton()));
		appendChild(
			new DomActionBar(//
				new SetDisabledButton(false),
				new SetDisabledButton(true)));
	}

	private class SetValueButton extends DomButton {

		public SetValueButton() {

			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("set value..."));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executePrompt(//
				input::setValue,
				IDisplayString.create("enter value"),
				"");
		}
	}

	private class GetSelectionValueButton extends DomButton {

		public GetSelectionValueButton() {

			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("alert: value"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.format("value: [%s]", input.getSelection().getValueOrNull()));
		}
	}

	private class GetValueStringOrNullButton extends DomButton {

		public GetValueStringOrNullButton() {

			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("alert: valueStringOrNull"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.format("valueOrNull: [%s]", input.getValueOrNull()));
		}
	}

	private class GetValueStringOrEmptyButton extends DomButton {

		public GetValueStringOrEmptyButton() {

			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("alert: valueStringOrEmpty"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.format("valueOrEmpty: [%s]", input.getValue().orElse("")));
		}
	}

	private class GetRawValueStringButton extends DomButton {

		public GetRawValueStringButton() {

			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("alert: rawValueString"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.format("valueText: [%s]", input.getInputField().getValueText()));
		}
	}

	private class FireAssertionButton extends DomButton {

		public FireAssertionButton() {

			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("alert: assert"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			input.getSelection().assertValid();
			executeAlert(IDisplayString.create("assertion passed"));
		}
	}

	private class SetDisabledButton extends DomButton {

		private final boolean disabled;

		public SetDisabledButton(boolean disabled) {

			this.disabled = disabled;
			setIcon(DomElementsImages.INFO.getResource());
			setLabel(IDisplayString.create("set disabled: " + disabled));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			input.setDisabled(disabled);
		}
	}
}
