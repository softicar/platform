package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;

class DomAutoCompletePopupValueDisplay<T> extends DomSpan implements IDomClickEventHandler {

	private final DomAutoCompleteInput<T> input;
	private final T value;

	public DomAutoCompletePopupValueDisplay(DomAutoCompleteInput<T> input, T value, String pattern) {

		this.input = input;
		this.value = value;

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_VALUE);

		var text = input.getInputEngine().getDisplayString(value).toString();
		var index = pattern.isEmpty()? -1 : text.toLowerCase().indexOf(pattern);
		if (index >= 0) {
			var prefix = text.substring(0, index);
			var match = text.substring(index, index + pattern.length());
			var suffix = text.substring(index + pattern.length());

			appendText(prefix);
			appendChild(new MatchSpan(match));
			appendText(suffix);
		} else {
			appendText(text);
		}

		getDomEngine().setPreventDefaultOnMouseDown(this, true);
	}

	@Override
	public void handleClick(IDomEvent event) {

		apply();
	}

	public void apply() {

		input.onSelection(value);
	}

	public void setSelected(boolean selected) {

		if (selected) {
			addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_SELECTED_VALUE);
		} else {
			removeCssClass(DomCssClasses.DOM_AUTO_COMPLETE_SELECTED_VALUE);
		}
	}

	static class MatchSpan extends DomSpan {

		public MatchSpan(String text) {

			addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_MATCH);
			appendText(text);
		}
	}
}
