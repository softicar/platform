package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import java.util.function.Supplier;

public class DomAutoCompleteIndicator<T> extends DomDiv {

	private final Supplier<DomAutoCompleteValueAndState<T>> valueAndStateSupplier;

	public DomAutoCompleteIndicator(Supplier<DomAutoCompleteValueAndState<T>> valueAndStateSupplier) {

		this.valueAndStateSupplier = valueAndStateSupplier;
		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_INDICATOR_PARENT);
	}

	public void refresh() {

		removeChildren();
		appendIndicator();
	}

	private void appendIndicator() {

		var valueAndState = valueAndStateSupplier.get();
		if (valueAndState.isAmbiguous()) {
			appendChild(new Image(DomAutoCompleteIndicatorType.AMBIGUOUS));
		} else if (valueAndState.isIllegal()) {
			appendChild(new Image(DomAutoCompleteIndicatorType.ILLEGAL));
		}
	}

	private static class Image extends DomImage {

		public Image(DomAutoCompleteIndicatorType type) {

			super(type.getImage());

			addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_INDICATOR);
			addCssClass(type.getCssClass());
			setTitle(type.getTitle());
		}
	}
}
