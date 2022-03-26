package com.softicar.platform.emf.editor;

import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.IEmfDiagnostic;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;

public class EmfAttributeValueFrame<R extends IEmfTableRow<R, ?>, V> extends AbstractEmfAttributeValueFrame<R, V> {

	private final EmfAttributesDiv<R> attributesDiv;
	private final R row;
	private EmfAttributeValueMode valueMode;
	private IEmfInput<V> input;

	public EmfAttributeValueFrame(EmfAttributesDiv<R> attributesDiv, IEmfAttribute<R, V> attribute, R row, EmfAttributeValueMode valueMode) {

		super(attribute);

		this.attributesDiv = attributesDiv;
		this.row = row;
		this.valueMode = null;
		this.input = null;

		refresh(valueMode);
	}

	public void refresh(EmfAttributeValueMode valueMode) {

		if (valueMode != this.valueMode) {
			this.valueMode = valueMode;

			setDisplayNone(valueMode.isHidden());

			if (valueMode.isDisplay()) {
				refreshDisplay();
			} else if (valueMode.isInput()) {
				refreshInput();
			}
		} else {
			if (valueMode.isDisplay()) {
				refreshDisplay();
			} else if (valueMode.isInput()) {
				input.refreshInputConstraints();
			}
		}
	}

	private void refreshDisplay() {

		removeChildren();
		appendChild(attribute.createDisplay(row));
	}

	private void refreshInput() {

		if (input == null) {
			createInput();
		} else {
			updateInput();
		}

		if (!isInputAppended()) {
			removeChildren();
			appendChild(input);
		}
	}

	private void createInput() {

		this.input = attribute.createInput(row);
		input.setChangeCallback(attributesDiv::onInputValueChange);
		input.setMandatory(valueMode.isMandatory());
		input.setValue(attribute.getValue(row));
	}

	private void updateInput() {

		input.setMandatory(valueMode.isMandatory());
		input.refreshInputConstraints();
	}

	private boolean isInputAppended() {

		return input != null && input.getParent() != null;
	}

	public void executePostSaveHook() {

		if (isInputAppended()) {
			input.executePostSaveHook();
		}
	}

	public void applyToTableRow() {

		if (isInputAppended()) {
			attribute.setValue(row, input.getValue().orElse(null));
		}
	}

	public void showDiagnostics(IEmfValidationResult validationResult) {

		clear();

		for (IEmfDiagnostic diagnostic: validationResult.getDiagnostics(attribute)) {
			setErrorState();
			addMessage(diagnostic.getMessage());
		}
	}
}
