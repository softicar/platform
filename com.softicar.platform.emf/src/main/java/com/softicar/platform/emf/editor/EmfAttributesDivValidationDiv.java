package com.softicar.platform.emf.editor;

import com.softicar.platform.dom.elements.input.diagnostics.DomInputDiagnosticsDisplay;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.IEmfDiagnostic;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Set;

public class EmfAttributesDivValidationDiv<R extends IEmfTableRow<R, ?>> extends DomInputDiagnosticsDisplay {

	public EmfAttributesDivValidationDiv(IEmfValidationResult validationResult, Set<IEmfAttribute<?, ?>> alreadyShownAttributes) {

		if (validationResult.hasErrors()) {
			setErrorState();
		} else {
			setSuccessState();
		}

		for (IEmfAttribute<?, ?> attribute: validationResult.getAttributes()) {
			if (!alreadyShownAttributes.contains(attribute.getOriginalAttribute())) {
				for (IEmfDiagnostic diagnostic: validationResult.getDiagnostics(attribute)) {
					appendChild(new ErrorRow(diagnostic));
				}
			}
		}
	}

	private class ErrorRow extends DomInputDiagnosticsDisplay {

		public ErrorRow(IEmfDiagnostic diagnostic) {

			setErrorState();
			appendText(diagnostic.getMessage().toString());
		}
	}
}
