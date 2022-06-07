package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.AbstractDomValueInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import java.util.Optional;

public class EmfMultilineStringInput extends AbstractDomValueInput<String> implements IEmfInput<String> {

	private final TextArea textArea;

	public EmfMultilineStringInput() {

		this.textArea = new TextArea();
		this.textArea.setRowCount(5);
	}

	@Override
	public void setValue(String value) {

		textArea.setInputText(value != null? value : "");
	}

	@Override
	public Optional<String> getValue() {

		return Optional.of(textArea.getInputText());
	}

	public void setPlaceholder(IDisplayString placeholder) {

		textArea.setPlaceholder(placeholder);
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		textArea.setDisabled(disabled);
	}

	private class TextArea extends DomTextArea implements IDomChangeEventHandler {

		@Override
		public void handleChange(IDomEvent event) {

			executeChangeCallbacks();
		}
	}
}
