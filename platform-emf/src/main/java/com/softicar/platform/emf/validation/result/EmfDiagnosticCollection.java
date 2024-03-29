package com.softicar.platform.emf.validation.result;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import java.util.Collection;
import java.util.TreeSet;

public class EmfDiagnosticCollection {

	private final Collection<IEmfDiagnostic> diagnostics;

	public EmfDiagnosticCollection() {

		this.diagnostics = new TreeSet<>();
	}

	// ------------------------------ non-mutating ------------------------------ //

	public boolean hasErrors() {

		return diagnostics//
			.stream()
			.anyMatch(IEmfDiagnostic::isError);
	}

	public Collection<IEmfDiagnostic> getDiagnostics() {

		return diagnostics;
	}

	@Override
	public String toString() {

		return Imploder.implode(diagnostics, "\n");
	}

	// ------------------------------ mutating ------------------------------ //

	public void addDiagnostic(IEmfDiagnostic diagnostic) {

		diagnostics.add(diagnostic);
	}

	public void addError(IDisplayString message) {

		addDiagnostic(new EmfDiagnostic(EmfDiagnosticLevel.ERROR, message));
	}
}
