package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import java.util.ArrayList;
import java.util.List;

class DomAutoCompletePopup<T> extends DomDiv {

	private final DomAutoCompleteInput<T> input;
	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final List<DomAutoCompletePopupValueDisplay<T>> valueDisplays;
	private int selectionIndex;

	public DomAutoCompletePopup(DomAutoCompleteInput<T> input) {

		this.input = input;
		this.inputEngine = input.getInputEngine();
		this.valueDisplays = new ArrayList<>();
		this.selectionIndex = -1;

		addCssClass(DomElementsCssClasses.DOM_AUTO_COMPLETE_POPUP);
	}

	public void refresh() {

		clear();

		var pattern = input.getPattern();
		var values = inputEngine.findMatches(pattern, DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY + 1);
		if (values.isEmpty()) {
			appendChild(new DomAutoCompletePopupNoValuesDisplay(pattern));
		} else {
			values//
				.stream()
				.limit(DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY)
				.forEach(value -> addValueDisplay(value, pattern));
			if (!pattern.isEmpty()) {
				setSelectionIndex(0);
			}
			if (values.size() >= DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY) {
				appendChild(new DomAutoCompletePopupMoreValuesDisplay());
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

	private void addValueDisplay(T value, String pattern) {

		var display = new DomAutoCompletePopupValueDisplay<>(input, value, pattern);
		appendChild(display);
		valueDisplays.add(display);
	}
}
