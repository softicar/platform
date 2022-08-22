package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import java.util.Optional;

public class DomAutoCompleteIndicator<T> extends DomDiv {

	private final DomAutoCompleteInput<T> input;
	private final DomAutoCompleteInputValidationMode validationMode;
	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final IDomTextualInput inputField;

	public DomAutoCompleteIndicator(DomAutoCompleteInput<T> input) {

		this.input = input;
		this.validationMode = input.validationMode;
		this.inputEngine = input.inputEngine;
		this.inputField = input.getInputField();

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR_PARENT);

		refresh();
	}

	public void refresh() {

		removeChildren();
		getIndicatorType().map(Image::new).ifPresent(this::appendChild);
	}

	private Optional<DomAutoCompleteIndicatorType> getIndicatorType() {

		if (!input.isBlank() && !validationMode.isPermissive()) {
			var matches = input.inputEngine.findMatches(input.getPattern(), 2);
			if (matches.isEmpty()) {
				return DomAutoCompleteIndicatorType.ILLEGAL.asOptional();
			} else if (matches.size() >= 2) {
				var firstElement = matches.iterator().next();
				if (!matchesInput(firstElement)) {
					return DomAutoCompleteIndicatorType.AMBIGUOUS.asOptional();
				}
			}
		}
		return Optional.empty();
	}

	private boolean matchesInput(T element) {

		var elementDisplayString = inputEngine.getDisplayString(element).toString();
		return matchesInput(elementDisplayString);
	}

	private boolean matchesInput(String elementDisplayString) {

		return inputField.getValueText().trim().equalsIgnoreCase(elementDisplayString);
	}

	private static class Image extends DomImage {

		public Image(DomAutoCompleteIndicatorType type) {

			super(type.getImage());

			addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR);
			addCssClass(type.getCssClass());
			setTitle(type.getTitle());
		}
	}
}
