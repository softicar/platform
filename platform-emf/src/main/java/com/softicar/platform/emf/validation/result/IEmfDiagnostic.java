package com.softicar.platform.emf.validation.result;

import com.softicar.platform.common.core.i18n.IDisplayString;

public interface IEmfDiagnostic extends Comparable<IEmfDiagnostic> {

	EmfDiagnosticLevel getDiagnosticLevel();

	IDisplayString getMessage();

	boolean isError();
}
