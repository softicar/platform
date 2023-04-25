package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.event.DomKeys;
import com.softicar.platform.dom.event.IDomBlurEventHandler;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomInputEventHandler;
import com.softicar.platform.dom.event.IDomKeyDownEventHandler;
import com.softicar.platform.dom.event.IDomKeyUpEventHandler;
import com.softicar.platform.dom.input.DomTextInput;
import java.util.List;

class DomAutoCompleteInputField extends DomTextInput
		implements IDomInputEventHandler, IDomKeyDownEventHandler, IDomKeyUpEventHandler, IDomBlurEventHandler, IDomClickEventHandler {

	private final DomAutoCompleteInput<?> input;

	public DomAutoCompleteInputField(DomAutoCompleteInput<?> input) {

		this.input = input;

		addCssClass(DomCssClasses.DOM_AUTO_COMPLETE_INPUT_FIELD);

		addChangeCallback(input::onChange);
		setListenToKeys(List.of(DomKeys.ARROW_DOWN, DomKeys.ARROW_UP, DomKeys.ENTER, DomKeys.ESCAPE, DomKeys.TAB));
	}

	@Override
	public void handleInput(IDomEvent event) {

		input.onInput();
	}

	@Override
	public void handleKeyDown(IDomEvent event) {

		String key = event.getKey();
		if (key.equals(DomKeys.ARROW_DOWN)) {
			input.onArrowDown();
		} else if (key.equals(DomKeys.ARROW_UP)) {
			input.onArrowUp();
		} else if (key.equals(DomKeys.ENTER) || key.equals(DomKeys.TAB)) {
			input.onEnterOrTab();
		} else if (key.equals(DomKeys.ESCAPE)) {
			input.onEscape();
		}
	}

	@Override
	public void handleBlur(IDomEvent event) {

		input.onBlur();
	}

	@Override
	public void handleClick(IDomEvent event) {

		input.onClickOrFocusByTab();
	}

	@Override
	public void handleKeyUp(IDomEvent event) {

		if (event.getKey().equals(DomKeys.TAB)) {
			input.onClickOrFocusByTab();
		}
	}
}
