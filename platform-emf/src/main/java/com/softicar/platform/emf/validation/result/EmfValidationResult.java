package com.softicar.platform.emf.validation.result;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EmfValidationResult implements IEmfValidationResult {

	private final Map<IEmfAttribute<?, ?>, EmfDiagnosticCollection> diagnostics;

	public EmfValidationResult() {

		this.diagnostics = new HashMap<>();
	}

	@Override
	public boolean hasErrors() {

		return diagnostics//
			.values()
			.stream()
			.anyMatch(EmfDiagnosticCollection::hasErrors);
	}

	@Override
	public Collection<IEmfAttribute<?, ?>> getAttributes() {

		return diagnostics.keySet();
	}

	@Override
	public Collection<IEmfDiagnostic> getDiagnostics(IEmfAttribute<?, ?> attribute) {

		EmfDiagnosticCollection result = diagnostics.get(attribute.getOriginalAttribute());
		return result != null? result.getDiagnostics() : Collections.emptyList();
	}

	@Override
	public void add(IEmfAttribute<?, ?> attribute, IEmfDiagnostic diagnostic) {

		diagnostics.computeIfAbsent(attribute.getOriginalAttribute(), it -> new EmfDiagnosticCollection()).addDiagnostic(diagnostic);

		IEmfAttribute<?, ?> originalAttribute = attribute.getOriginalAttribute();
		if (originalAttribute != attribute) {
			add(originalAttribute, diagnostic);
		}
	}

	@Override
	public void addAll(IEmfValidationResult result) {

		for (IEmfAttribute<?, ?> attribute: result.getAttributes()) {
			for (IEmfDiagnostic diagnostic: result.getDiagnostics(attribute)) {
				add(attribute, diagnostic);
			}
		}
	}

	@Override
	public String toString() {

		return Imploder.implode(diagnostics.values(), "\n");
	}

	public void assertError(IDisplayString expectedMessage) {

		if (getDiagnostics()//
			.stream()
			.filter(it -> it.getDiagnosticLevel() == EmfDiagnosticLevel.ERROR)
			.noneMatch(it -> it.getMessage().equals(expectedMessage))) {
			throw new AssertionError("Missing expected error message: %s".formatted(expectedMessage));
		}
	}
}
