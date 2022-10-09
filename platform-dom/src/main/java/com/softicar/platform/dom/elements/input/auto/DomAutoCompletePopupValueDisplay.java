package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.elements.input.auto.pattern.MultiPatternMatcher;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Map;

class DomAutoCompletePopupValueDisplay<T> extends DomSpan implements IDomClickEventHandler {

	private final DomAutoCompleteInput<T> input;
	private final T value;

	public DomAutoCompletePopupValueDisplay(DomAutoCompleteInput<T> input, T value, String pattern) {

		this.input = input;
		this.value = value;

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_VALUE);

		var text = input.getInputEngine().getDisplayString(value).toString();

		var matches = new MultiPatternMatcher<>(Map.of(text, value)).findMatches(pattern, 1);
		if (!matches.isEmpty()) {
			var ranges = matches.get(0).getRanges();
			var rangeIterator = ranges.iterator();
			int cursor = 0;
			while (rangeIterator.hasNext()) {
				var range = rangeIterator.next();
				int fromIndex = range.getFromIndex();
				int toIndex = range.getToIndex();
				appendText(text.substring(cursor, fromIndex));
				appendChild(new MatchSpan(text.substring(fromIndex, toIndex)));
				cursor = toIndex;
			}
			appendChild(text.substring(cursor));
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
