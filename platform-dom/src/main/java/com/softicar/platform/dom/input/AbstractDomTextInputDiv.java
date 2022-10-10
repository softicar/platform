package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Optional;

public abstract class AbstractDomTextInputDiv extends AbstractDomValueInputDiv<String> implements IDomTextualInput, IDomChangeEventHandler {

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
	public String getValueText() {

		return input.getValueText();
	}

	@Override
	public String getValueTextTrimmed() {

		return input.getValueTextTrimmed();
	}

	@Override
	public boolean isBlank() {

		return input.isBlank();
	}

	@Override
	public void selectText() {

		input.selectText();
	}

	@Override
	public IDomTextualInput setPlaceholder(IDisplayString placeholder) {

		return input.setPlaceholder(placeholder);
	}

	@Override
	public void setReadonly(boolean readonly) {

		input.setReadonly(readonly);
	}

	@Override
	public boolean isReadonly() {

		return input.isReadonly();
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
