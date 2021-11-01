package com.softicar.platform.emf.validation.result;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

public interface IEmfValidationResult {

	boolean hasErrors();

	Collection<IEmfAttribute<?, ?>> getAttributes();

	Collection<IEmfDiagnostic> getDiagnostics(IEmfAttribute<?, ?> attribute);

	default Collection<IEmfDiagnostic> getDiagnostics(IDbField<?, ?> field) {

		return getAttributes()//
			.stream()
			.filter(attribute -> attribute.getField().map(attributeField -> attributeField == field).orElse(false))
			.flatMap(attribute -> getDiagnostics(attribute).stream())
			.collect(Collectors.toCollection(TreeSet::new));
	}

	default Collection<IEmfDiagnostic> getDiagnostics() {

		return getAttributes()//
			.stream()
			.flatMap(attribute -> getDiagnostics(attribute).stream())
			.collect(Collectors.toCollection(TreeSet::new));
	}

	void add(IEmfAttribute<?, ?> attribute, IEmfDiagnostic diagnostic);

	void addAll(IEmfValidationResult result);

	default void addError(IEmfAttribute<?, ?> attribute, IDisplayString message) {

		add(attribute, new EmfDiagnostic(EmfDiagnosticLevel.ERROR, message));
	}

	default void addWarning(IEmfAttribute<?, ?> attribute, IDisplayString message) {

		add(attribute, new EmfDiagnostic(EmfDiagnosticLevel.WARNING, message));
	}
}
