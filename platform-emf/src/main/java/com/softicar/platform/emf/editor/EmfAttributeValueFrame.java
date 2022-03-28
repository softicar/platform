package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.IEmfDiagnostic;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;

public class EmfAttributeValueFrame<R extends IEmfTableRow<R, ?>, V> extends AbstractEmfAttributeValueFrame<R, V> {

	private final INullaryVoidFunction changeCallback;
	private final R row;
	private EmfAttributeValueMode valueMode;
	private IEmfInput<V> input;

	public EmfAttributeValueFrame(INullaryVoidFunction changeCallback, IEmfAttribute<R, V> attribute, R row, EmfAttributeValueMode valueMode) {

		super(attribute);

		this.changeCallback = changeCallback;
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
				enterDisplayMode();
			} else if (valueMode.isInput()) {
				enterInputMode();
			}
		} else {
			if (valueMode.isDisplay()) {
				refreshDisplay();
			} else if (valueMode.isInput()) {
				input.refreshInputConstraints();
			}
		}
	}

	private void enterDisplayMode() {

		refreshDisplay();
	}

	private void refreshDisplay() {

		removeChildren();
		appendChild(attribute.createDisplay(row));
	}

	private void enterInputMode() {

		if (input == null) {
			createInput();
		} else {
			updateInput();
		}

		if (input.getParent() == null) {
			removeChildren();
			appendChild(input);
		}
	}

	private void createInput() {

		this.input = attribute.createInput(row);
		input.setChangeCallback(changeCallback);
		input.setMandatory(valueMode.isMandatory());
		input.setValue(attribute.getValue(row));
	}

	private void updateInput() {

		input.setMandatory(valueMode.isMandatory());
		input.refreshInputConstraints();
	}

	public void executePostSaveHook() {

		if (valueMode.isInput()) {
			input.executePostSaveHook();
		}
	}

	public void applyToTableRow() {

		if (valueMode.isInput()) {
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
