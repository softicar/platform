package com.softicar.platform.emf.attribute.field.foreign.entity.input;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.entity.table.IEmfEntityTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmfEntityInputEngine<T extends IEmfEntity<T, ?>, S> extends DomAutoCompleteDefaultInputEngine<T> {

	private final IEmfEntityTable<T, ?, S> targetTable;
	private final Collection<Predicate<T>> validators;

	public EmfEntityInputEngine(IEmfEntityTable<T, ?, S> targetTable, S scope) {

		this.targetTable = targetTable;
		this.validators = new ArrayList<>();

		setLoader(() -> loadValues(scope));
		addDependsOn(targetTable);
	}

	public EmfEntityInputEngine(IEmfEntityTable<T, ?, S> targetTable) {

		this(targetTable, null);
	}

	public EmfEntityInputEngine<T, S> addValidator(Predicate<T> validator) {

		validators.add(validator);
		return this;
	}

	private Collection<T> loadValues(S scope) {

		var scopeField = targetTable.getScopeField();
		var deactivation = targetTable.getEmfTableConfiguration().getDeactivationStrategy();

		return targetTable//
			.createSelect()
			.customize(deactivation::addWhereActive)
			.whereIf(scopeField.isPresent() && scope != null, () -> scopeField.get().isEqual(scope))
			.stream()
			.filter(this::isValid)
			.collect(Collectors.toList());
	}

	private boolean isValid(T targetEntity) {

		return validators.stream().allMatch(validator -> validator.test(targetEntity));
	}
}
