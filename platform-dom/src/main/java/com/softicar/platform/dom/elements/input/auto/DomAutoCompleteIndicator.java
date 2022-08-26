package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomImage;

public class DomAutoCompleteIndicator<T> extends DomDiv {

	private final DomAutoCompleteInput<T> input;

	public DomAutoCompleteIndicator(DomAutoCompleteInput<T> input) {

		this.input = input;

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR_PARENT);
	}

	public void refresh() {

		removeChildren();
		appendIndicator();
	}

	private void appendIndicator() {

		var valueAndState = new DomAutoCompleteValueParser<>(input).parse();

		if (valueAndState.isAmbiguous()) {
			appendChild(new Image(DomAutoCompleteIndicatorType.AMBIGUOUS));
		} else if (valueAndState.isIllegal()) {
			appendChild(new Image(DomAutoCompleteIndicatorType.ILLEGAL));
		}
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
