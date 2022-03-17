package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.ajax.image.AjaxImages;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputIndicatorMode;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;

public class AutoCompleteTestCase extends AbstractTestCaseDiv {

	private final AutoCompleteTestCaseInput input;

	public AutoCompleteTestCase() {

		appendChild(new DomDiv()).appendText("Auto complete without change handler:");

		this.input = appendChild(new AutoCompleteTestCaseInput(this));

		appendChild(
			new DomActionBar(//
				new DomActionBar(//
					new SetIndicatorModeButton(DomAutoCompleteInputIndicatorMode.GENERIC),
					new SetIndicatorModeButton(DomAutoCompleteInputIndicatorMode.VALIDATION))));
		appendChild(
			new DomActionBar(//
				new SetValidationModeButton(DomAutoCompleteInputValidationMode.DEDUCTIVE),
				new SetValidationModeButton(DomAutoCompleteInputValidationMode.PERMISSIVE),
				new SetValidationModeButton(DomAutoCompleteInputValidationMode.RESTRICTIVE)));
		appendChild(
			new DomActionBar(//
				new SetValueMandatoryButton(true),
				new SetValueMandatoryButton(false)));
		appendChild(
			new DomActionBar(//
				new MarkAsInvalidButton(),
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
				new SetEnabledButton(true),
				new SetEnabledButton(false)));
	}

	private class MarkAsInvalidButton extends DomButton {

		public MarkAsInvalidButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("mark as invalid"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			getDomEngine().setAutoCompleteInputInvalid(input);
		}
	}

	private class SetValueButton extends DomButton {

		public SetValueButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
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

	private class SetValueMandatoryButton extends DomButton {

		private final boolean mandatory;

		public SetValueMandatoryButton(boolean mandatory) {

			this.mandatory = mandatory;
			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("set mandatory: " + mandatory));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			input.getConfiguration().setMandatory(mandatory);
		}
	}

	private class SetValidationModeButton extends DomButton {

		private final DomAutoCompleteInputValidationMode mode;

		public SetValidationModeButton(DomAutoCompleteInputValidationMode mode) {

			this.mode = mode;
			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("set validation mode: " + mode.name()));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			input.getConfiguration().setValidationMode(mode);
		}
	}

	private class SetIndicatorModeButton extends DomButton {

		private final DomAutoCompleteInputIndicatorMode mode;

		public SetIndicatorModeButton(DomAutoCompleteInputIndicatorMode mode) {

			this.mode = mode;
			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("set indicator mode: " + mode.name()));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			input.getConfiguration().setIndicatorMode(mode);
		}
	}

	private class GetSelectionValueButton extends DomButton {

		public GetSelectionValueButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("alert: value"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.format("value: [%s]", input.getSelection().getValueOrNull()));
		}
	}

	private class GetValueStringOrNullButton extends DomButton {

		public GetValueStringOrNullButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("alert: valueStringOrNull"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.format("valueStringOrNull: [%s]", input.getValueStringOrNull()));
		}
	}

	private class GetValueStringOrEmptyButton extends DomButton {

		public GetValueStringOrEmptyButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("alert: valueStringOrEmpty"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.format("valueStringOrEmpty: [%s]", input.getValueStringOrEmpty()));
		}
	}

	private class GetRawValueStringButton extends DomButton {

		public GetRawValueStringButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("alert: rawValueString"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			executeAlert(IDisplayString.format("rawValueString: [%s]", input.getRawValueString()));
		}
	}

	private class FireAssertionButton extends DomButton {

		public FireAssertionButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("alert: assert"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			input.getSelection().assertValid();
			executeAlert(IDisplayString.create("assertion passed"));
		}
	}

	private class SetEnabledButton extends DomButton {

		private final boolean enabled;

		public SetEnabledButton(boolean enabled) {

			this.enabled = enabled;
			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("set enabled: " + enabled));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			input.setEnabledRecursively(enabled);
		}
	}
}
