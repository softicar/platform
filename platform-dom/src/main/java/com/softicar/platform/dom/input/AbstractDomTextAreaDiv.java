package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Optional;

public abstract class AbstractDomTextAreaDiv extends AbstractDomValueInputDiv<String> implements IDomTextualInput, IDomChangeEventHandler {

	protected final DomTextArea textArea;

	public AbstractDomTextAreaDiv() {

		this.textArea = new DomTextArea();
		this.textArea.addChangeCallback(this::executeChangeCallbacks);

		appendChild(textArea);
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
	public String getValueText() {

		return textArea.getValueText();
	}

	@Override
	public String getValueTextTrimmed() {

		return textArea.getValueTextTrimmed();
	}

	@Override
	public boolean isBlank() {

		return textArea.isBlank();
	}

	@Override
	public void selectText() {

		textArea.selectText();
	}

	@Override
	public IDomTextualInput setPlaceholder(IDisplayString placeholder) {

		return textArea.setPlaceholder(placeholder);
	}

	@Override
	public void setReadonly(boolean readonly) {

		textArea.setReadonly(readonly);
	}

	@Override
	public boolean isReadonly() {

		return textArea.isReadonly();
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
