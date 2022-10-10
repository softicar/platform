package com.softicar.platform.dom.input;

import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Optional;

public abstract class AbstractDomTextInputDiv extends AbstractDomValueInputDiv<String> implements IDomChangeEventHandler {

	protected final DomTextInput input;

	public AbstractDomTextInputDiv() {

		this.input = new DomTextInput();
		this.input.addChangeCallback(this::executeChangeCallbacks);

		appendChild(input);
	}

	@Override
	public void setValue(String value) {

		input.setValue(value);
	}

	@Override
	public Optional<String> getValue() {

		return input.getValue();
	}

	@Override
	public void handleChange(IDomEvent event) {

		executeChangeCallbacks();
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		input.doSetDisabled(disabled);
	}
}
