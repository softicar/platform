package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.auto.matching.AutoCompleteMatch;
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

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_POPUP);
	}

	public void refresh() {

		clear();

		var pattern = input.getPattern();
		var matches = inputEngine.findMatches(pattern, DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY + 1);
		if (matches.isEmpty()) {
			appendChild(new DomAutoCompletePopupNoValuesDisplay(pattern));
		} else {
			matches//
				.getAll()
				.stream()
				.limit(DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY)
				.forEach(this::addValueDisplay);
			if (!pattern.isEmpty()) {
				setSelectionIndex(0);
			}
			if (matches.size() >= DomAutoCompleteInput.MAXIMUM_ELEMENTS_TO_DISPLAY) {
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

	private void addValueDisplay(AutoCompleteMatch<T> match) {

		T value = match.getValue();
		var matchRanges = match.getAllMatchRanges();
		var display = new DomAutoCompletePopupValueDisplay<>(input, value, matchRanges);
		appendChild(display);
		valueDisplays.add(display);
	}
}
