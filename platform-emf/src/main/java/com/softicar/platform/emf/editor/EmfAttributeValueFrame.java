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

			removeChildren();
			if (valueMode.isDisplay()) {
				refreshDisplay();
			} else if (valueMode.isInput()) {
				appendInput();
			}
		} else {
			if (valueMode.isHidden()) {
				// nothing to refresh
			} else if (isInputAppended()) {
				input.refreshInputConstraints();
			} else {
				refreshDisplay();
			}
		}
	}

	private void refreshDisplay() {

		removeChildren();

		attribute//
			.createDisplay(row)
			.ifPresent(this::appendChild);
	}

	private void appendInput() {

		if (input == null) {
			this.input = attribute.createInput(row).orElse(null);

			if (input != null) {
				input.setChangeCallback(attributesDiv::onInputValueChange);
				input.setMandatory(valueMode.isMandatory());
				input.setValue(attribute.getValue(row));
				appendChild(input);
			} else {
				refreshDisplay();
			}
		} else {
			input.setMandatory(valueMode.isMandatory());
			input.refreshInputConstraints();

			// TODO check if input is already appended
			appendChild(input);
		}
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
