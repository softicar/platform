package com.softicar.platform.emf.validation.result;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Comparator;
import java.util.Objects;

public class EmfDiagnostic implements IEmfDiagnostic {

	private final EmfDiagnosticLevel diagnosticLevel;
	private final IDisplayString message;

	public EmfDiagnostic(EmfDiagnosticLevel diagnosticLevel, IDisplayString message) {

		this.diagnosticLevel = diagnosticLevel;
		this.message = message;
	}

	@Override
	public EmfDiagnosticLevel getDiagnosticLevel() {

		return diagnosticLevel;
	}

	@Override
	public IDisplayString getMessage() {

		return message;
	}

	@Override
	public boolean isError() {

		return getDiagnosticLevel() == EmfDiagnosticLevel.ERROR;
	}

	@Override
	public String toString() {

		return String.format("%s: %s", diagnosticLevel, message.toString());
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof EmfDiagnostic) {
			EmfDiagnostic other = (EmfDiagnostic) object;
			return Objects.equals(diagnosticLevel, other.getDiagnosticLevel()) && Objects.equals(message.toString(), other.getMessage().toString());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(diagnosticLevel, message.toString());
	}

	@Override
	public int compareTo(IEmfDiagnostic other) {

		return Comparator//
			.comparing(IEmfDiagnostic::getDiagnosticLevel)
			.thenComparing(diagnostic -> diagnostic.getMessage().toString())
			.compare(this, other);
	}
}
