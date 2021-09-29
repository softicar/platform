package com.softicar.platform.dom.elements.input.diagnostics;

import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.style.ICssClass;
import java.util.Optional;

public enum DomInputDiagnosticsState {

	NONE(null),
	SUCCESS(DomCssPseudoClasses.SUCCESS),
	WARNING(DomCssPseudoClasses.WARNING),
	ERROR(DomCssPseudoClasses.ERROR);

	private Optional<ICssClass> cssClass;

	private DomInputDiagnosticsState(ICssClass cssClass) {

		this.cssClass = Optional.ofNullable(cssClass);
	}

	public Optional<ICssClass> getCssClass() {

		return cssClass;
	}
}
