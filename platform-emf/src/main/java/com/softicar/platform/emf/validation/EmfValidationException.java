package com.softicar.platform.emf.validation;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.validation.result.IEmfDiagnostic;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.ArrayList;
import java.util.List;

public class EmfValidationException extends SofticarUserException {

	private final IEmfValidationResult validationResult;

	public EmfValidationException(IEmfTable<?, ?, ?> table, IEmfValidationResult validationResult) {

		super(new Message(table, validationResult));

		this.validationResult = validationResult;
	}

	public IEmfValidationResult getValidationResult() {

		return validationResult;
	}

	private static class Message implements IDisplayString {

		private final List<IDisplayString> lines = new ArrayList<>();

		public Message(IEmfTable<?, ?, ?> table, IEmfValidationResult validationResult) {

			lines.add(EmfI18n.VALIDATION_OF_ARG1_FAILED.toDisplay(table.getTitle()).concat(":"));
			for (IEmfDiagnostic diagnostic: validationResult.getDiagnostics()) {
				lines
					.add(
						new DisplayString()//
							.append("  * ")
							.append(diagnostic.getMessage()));
			}
		}

		@Override
		public String toString() {

			return Imploder.implode(lines, "\n");
		}
	}
}
