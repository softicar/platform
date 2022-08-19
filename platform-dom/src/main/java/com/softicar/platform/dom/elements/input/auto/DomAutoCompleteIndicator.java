package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputConfiguration;

public class DomAutoCompleteIndicator<T> extends DomDiv {

	private final DomAutoCompleteInput<T> input;
	private final IDomAutoCompleteInputConfiguration configuration;
	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final IDomTextualInput inputField;

	public DomAutoCompleteIndicator(DomAutoCompleteInput<T> input) {

		this.input = input;
		this.configuration = input.getConfiguration();
		this.inputEngine = input.inputEngine;
		this.inputField = input.getInputField();

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR);
	}

	public void refresh() {

		if (input.isBlank()) {
			if (configuration.isMandatory()) {
				showIndicator(DomAutoCompleteIndicatorType.MISSING);
			} else {
				showIndicator(DomAutoCompleteIndicatorType.VALID);
			}
		} else if (configuration.getValidationMode().isPermissive()) {
			showIndicator(DomAutoCompleteIndicatorType.VALID);
		} else {
			var pattern = input.getPattern();
			var matches = input.inputEngine.findMatches(pattern, 2);
			if (matches.size() == 0) {
				showIndicator(DomAutoCompleteIndicatorType.ILLEGAL);
			} else if (matches.size() == 1) {
				var element = matches.iterator().next();
				if (configuration.getValidationMode().isRestrictive()) {
					if (matchesInput(element)) {
						showIndicator(DomAutoCompleteIndicatorType.VALID);
					} else {
						showIndicator(DomAutoCompleteIndicatorType.ILLEGAL);
					}
				} else {
					showIndicator(DomAutoCompleteIndicatorType.VALID);
				}
			} else {
				var firstElement = matches.iterator().next();
				if (matchesInput(firstElement)) {
					showIndicator(DomAutoCompleteIndicatorType.VALID);
				} else {
					if (input.hasFocus()) {
						showIndicator(DomAutoCompleteIndicatorType.AMBIGUOUS);
					} else {
						showIndicator(DomAutoCompleteIndicatorType.ILLEGAL);
					}
				}
			}
		}
	}

	private boolean matchesInput(T element) {

		var elementDisplayString = inputEngine.getDisplayString(element).toString();
		return matchesInput(elementDisplayString);
	}

	private boolean matchesInput(String elementDisplayString) {

		if (configuration.getValidationMode().isRestrictive()) {
			return inputField.getValueText().equals(elementDisplayString);
		} else {
			return inputField.getValueText().trim().equalsIgnoreCase(elementDisplayString);
		}
	}

	private void showIndicator(DomAutoCompleteIndicatorType type) {

		removeChildren();

		if (!input.hasFocus()) {
			if (type == DomAutoCompleteIndicatorType.ILLEGAL || type == DomAutoCompleteIndicatorType.AMBIGUOUS) {
				type = DomAutoCompleteIndicatorType.NOT_OKAY;
			}
		}

		appendChild(new Image(type));
	}

	private class Image extends DomImage {

		public Image(DomAutoCompleteIndicatorType type) {

			super(type.getImage());

			addCssClass(type.getCssClass());
			setTitle(type.getTitle());
		}
	}
}
