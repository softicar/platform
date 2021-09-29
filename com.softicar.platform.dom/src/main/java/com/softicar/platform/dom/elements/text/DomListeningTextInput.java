package com.softicar.platform.dom.elements.text;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.event.IDomEnterKeyEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomTextInput;

/**
 * A {@link DomTextInput} that listened for ENTER events.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DomListeningTextInput extends DomTextInput implements IDomEnterKeyEventHandler {

	private final INullaryVoidFunction callback;

	public DomListeningTextInput(INullaryVoidFunction callback) {

		this.callback = callback;
	}

	@Override
	public void handleEnterKey(IDomEvent event) {

		if (callback != null) {
			callback.apply();
		}
	}
}
