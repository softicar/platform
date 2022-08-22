package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomSpan;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.ArrayList;
import java.util.List;

class DomAutoCompletePopup<T> extends DomDiv {

	private final DomAutoCompleteInput<T> input;
	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final List<ValueDisplay> valueDisplays;
	private int selectionIndex;
	private String pattern;

	public DomAutoCompletePopup(DomAutoCompleteInput<T> input) {

		this.input = input;
		this.inputEngine = input.getInputEngine();
		this.valueDisplays = new ArrayList<>();
		this.selectionIndex = -1;
		this.pattern = "";

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_POPUP);
	}

	public void refresh() {

		clear();
		updatePattern();

		var values = inputEngine.findMatches(pattern, DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY + 1);
		if (values.isEmpty()) {
			appendChild(new NoValuesDisplay());
		} else {
			values//
				.stream()
				.limit(DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY)
				.forEach(this::addValueDisplay);
			if (!pattern.isEmpty()) {
				setSelectionIndex(0);
			}
			if (values.size() >= DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY) {
				appendChild(new MoreValuesDisplay());
			}
		}
	}

	public void applySelection() {

		if (selectionIndex >= 0) {
			valueDisplays.get(selectionIndex).apply();
		}
	}

	public void moveSelectionDown() {

		setSelectionIndex(selectionIndex + 1);
	}

	public void moveSelectionUp() {

		setSelectionIndex(selectionIndex - 1);
	}

	private void setSelectionIndex(int index) {

		if (!valueDisplays.isEmpty()) {
			if (index < 0) {
				index = valueDisplays.size() - 1;
			} else if (index >= valueDisplays.size()) {
				index = 0;
			}
			if (selectionIndex >= 0) {
				valueDisplays.get(selectionIndex).setSelected(false);
			}
			selectionIndex = index;
			valueDisplays.get(selectionIndex).setSelected(true);
		}
	}

	private void clear() {

		removeChildren();
		valueDisplays.clear();
		selectionIndex = -1;
	}

	private void updatePattern() {

		this.pattern = input.getPattern();
	}

	private void addValueDisplay(T value) {

		var display = new ValueDisplay(value);
		appendChild(display);
		valueDisplays.add(display);
	}

	private class NoValuesDisplay extends DomDiv {

		public NoValuesDisplay() {

			addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_NO_VALUES);
			appendChild(getText());
		}

		private IDisplayString getText() {

			if (pattern.isEmpty()) {
				return DomI18n.NO_RECORDS_FOUND.encloseInParentheses();
			} else {
				return IDisplayString.create(pattern).concatColon().concatSentence(DomI18n.NO_RECORDS_FOUND);
			}
		}
	}

	private class MoreValuesDisplay extends DomDiv {

		public MoreValuesDisplay() {

			addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_MORE_VALUES);
			appendChild(DomI18n.FURTHER_ENTRIES_AVAILABLE);
		}
	}

	private class ValueDisplay extends DomSpan implements IDomClickEventHandler {

		private final T value;

		public ValueDisplay(T value) {

			this.value = value;

			addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_VALUE);

			var text = inputEngine.getDisplayString(value).toString();
			var index = pattern.isEmpty()? -1 : text.toLowerCase().indexOf(pattern);
			if (index >= 0) {
				var prefix = text.substring(0, index);
				var match = text.substring(index, index + pattern.length());
				var suffix = text.substring(index + pattern.length());

				appendText(prefix);
				appendChild(new ValueMatchSpan(match));
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
				addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_SELECTED_VALUE);
			} else {
				removeCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_SELECTED_VALUE);
			}
		}
	}

	private class ValueMatchSpan extends DomSpan {

		public ValueMatchSpan(String text) {

			addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_MATCH);
			appendText(text);
		}
	}
}
