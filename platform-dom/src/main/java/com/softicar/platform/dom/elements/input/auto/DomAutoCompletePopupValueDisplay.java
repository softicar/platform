package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.elements.input.auto.pattern.MultiPatternMatcher;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.IntStream;

class DomAutoCompletePopupValueDisplay<T> extends DomSpan implements IDomClickEventHandler {

	private final DomAutoCompleteInput<T> input;
	private final T value;

	public DomAutoCompletePopupValueDisplay(DomAutoCompleteInput<T> input, T value, String pattern) {

		this.input = input;
		this.value = value;

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_VALUE);

		var text = input.getInputEngine().getDisplayString(value).toString();

		var matcher = new MultiPatternMatcher<>(Map.of(text, value)).setIgnoreDiacritics(true);
		var matches = matcher.findMatches(pattern, 1);
		if (!matches.isEmpty()) {
			var matchedIndexes = new TreeSet<Integer>();
			for (var range: matches.get(0).getRanges()) {
				IntStream//
					.range(range.getFromIndex(), range.getToIndex())
					.boxed()
					.forEach(matchedIndexes::add);
			}

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
