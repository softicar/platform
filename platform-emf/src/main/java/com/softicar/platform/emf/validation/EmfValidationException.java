package com.softicar.platform.emf.validation;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.validation.result.IEmfDiagnostic;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.ArrayList;
import java.util.List;

public class EmfValidationException extends SofticarUserException {

	private final IEmfValidationResult validationResult;

	public EmfValidationException(IEmfValidationResult validationResult) {

		super(new Message(validationResult));

		this.validationResult = validationResult;
	}

	public IEmfValidationResult getValidationResult() {

		return validationResult;
	}

	private static class Message implements IDisplayString {

		private final List<IDisplayString> lines = new ArrayList<>();

		public Message(IEmfValidationResult validationResult) {

			lines.add(EmfI18n.VALIDATION_FAILED.concat(":"));
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
