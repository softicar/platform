package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.elements.input.auto.matching.AutoCompleteMatchRange;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;

class DomAutoCompletePopupValueDisplay<T> extends DomSpan implements IDomClickEventHandler {

	private final DomAutoCompleteInput<T> input;
	private final T value;

	public DomAutoCompletePopupValueDisplay(DomAutoCompleteInput<T> input, T value, List<AutoCompleteMatchRange> ranges) {

		this.input = input;
		this.value = value;

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_VALUE);

		var text = input.getInputEngine().getDisplayString(value).toString();

		if (!ranges.isEmpty()) {
			var matchedIndexes = new TreeSet<Integer>();
			ranges.forEach(range -> {
				IntStream//
					.range(range.getLowerIndex(), range.getUpperIndex())
					.boxed()
					.forEach(matchedIndexes::add);
			});

			for (int i = 0; i < text.length(); i++) {
				String character = text.charAt(i) + "";
				if (matchedIndexes.contains(i)) {
					appendChild(new MatchSpan(character));
				} else {
					appendText(character);
				}
			}
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
