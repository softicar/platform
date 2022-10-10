package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Optional;

public abstract class AbstractDomTextAreaDiv extends AbstractDomValueInputDiv<String> implements IDomChangeEventHandler {

	protected final DomTextArea textArea;

	public AbstractDomTextAreaDiv() {

		this.textArea = new DomTextArea();
		this.textArea.addChangeCallback(this::executeChangeCallbacks);

		appendChild(textArea);
	}

	public AbstractDomTextAreaDiv setPlaceholder(IDisplayString placeholder) {

		textArea.setPlaceholder(placeholder);
		return this;
	}

	public AbstractDomTextAreaDiv setSize(int rows, int columns) {

		textArea.setSize(rows, columns);
		return this;
	}

	public AbstractDomTextAreaDiv setRowCount(int row) {

		textArea.setRowCount(row);
		return this;
	}

	public AbstractDomTextAreaDiv setColCount(int col) {

		textArea.setColCount(col);
		return this;
	}

	@Override
	public void setValue(String value) {

		textArea.setValue(value);
	}

	@Override
	public Optional<String> getValue() {

		return textArea.getValue();
	}

	@Override
	public void handleChange(IDomEvent event) {

		executeChangeCallbacks();
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		textArea.doSetDisabled(disabled);
	}
}
