package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.IEmfDiagnostic;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;

public class EmfAttributeValueInputFrame<R extends IEmfTableRow<R, ?>, V> extends AbstractEmfAttributeValueFrame<R, V> {

	private final R row;
	private final IEmfInput<V> input;

	public EmfAttributeValueInputFrame(IEmfAttribute<R, V> attribute, R row, IEmfInput<V> input) {

		super(attribute);

		this.row = row;
		this.input = input;

		input.setMandatory(attribute.isMandatory(row));
		appendChild(input);
	}

	public IEmfInput<V> getInput() {

		return input;
	}

	public void setChangeCallback(INullaryVoidFunction callback) {

		input.setChangeCallback(callback);
	}

	public void refreshInputConstraints() {

		input.refreshInputConstraints();
	}

	public void executePostSaveHook() {

		input.executePostSaveHook();
	}

	public void applyFromTableRow() {

		input.setValue(attribute.getValue(row));
	}

	public void applyToTableRow() {

		attribute.setValue(row, input.getValue().orElse(null));
	}

	public void showDiagnostics(IEmfValidationResult validationResult) {

		clear();

		for (IEmfDiagnostic diagnostic: validationResult.getDiagnostics(attribute)) {
			setErrorState();
			addMessage(diagnostic.getMessage());
		}
	}
}
