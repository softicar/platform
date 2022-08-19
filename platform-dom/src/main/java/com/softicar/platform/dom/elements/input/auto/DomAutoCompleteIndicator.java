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

		removeChildren();
		appendChild(new Image(getIndicatorType()));
	}

	private DomAutoCompleteIndicatorType getIndicatorType() {

		var type = getFocusAgnosticIndicatorType();

		if (type == DomAutoCompleteIndicatorType.ILLEGAL || type == DomAutoCompleteIndicatorType.AMBIGUOUS) {
			return input.hasFocus()? type : DomAutoCompleteIndicatorType.NOT_OKAY;
		} else {
			return type;
		}
	}

	private DomAutoCompleteIndicatorType getFocusAgnosticIndicatorType() {

		if (input.isBlank()) {
			if (configuration.isMandatory()) {
				return DomAutoCompleteIndicatorType.MISSING;
			} else {
				return DomAutoCompleteIndicatorType.VALID;
			}
		} else if (configuration.getValidationMode().isPermissive()) {
			return DomAutoCompleteIndicatorType.VALID;
		} else {
			var pattern = input.getPattern();
			var matches = input.inputEngine.findMatches(pattern, 2);
			if (matches.size() == 0) {
				return DomAutoCompleteIndicatorType.ILLEGAL;
			} else if (matches.size() == 1) {
				var element = matches.iterator().next();
				if (configuration.getValidationMode().isRestrictive()) {
					if (matchesInput(element)) {
						return DomAutoCompleteIndicatorType.VALID;
					} else {
						return DomAutoCompleteIndicatorType.ILLEGAL;
					}
				} else {
					return DomAutoCompleteIndicatorType.VALID;
				}
			} else {
				var firstElement = matches.iterator().next();
				if (matchesInput(firstElement)) {
					return DomAutoCompleteIndicatorType.VALID;
				} else {
					return DomAutoCompleteIndicatorType.AMBIGUOUS;
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

	private static class Image extends DomImage {

		public Image(DomAutoCompleteIndicatorType type) {

			super(type.getImage());

			addCssClass(type.getCssClass());
			setTitle(type.getTitle());
		}
	}
}
