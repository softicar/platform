package com.softicar.platform.emf.attribute.field.foreign.entity;

import com.softicar.platform.emf.attribute.field.foreign.entity.scope.EmfDefaultForeignEntityScope;
import com.softicar.platform.emf.attribute.field.foreign.entity.scope.IEmfForeignEntityScope;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiPredicate;

public class EmfForeignEntityAttributeValidator<S extends IEmfTableRow<S, ?>, T extends IEmfTableRow<T, ?>> implements BiPredicate<S, T> {

	private IEmfForeignEntityScope<S, T> scope;
	private final Collection<BiPredicate<S, T>> predicates;

	public EmfForeignEntityAttributeValidator() {

		this.scope = new EmfDefaultForeignEntityScope<>();
		this.predicates = new ArrayList<>();
	}

	public EmfForeignEntityAttributeValidator<S, T> setScope(IEmfForeignEntityScope<S, T> scope) {

		this.scope = scope;
		return this;
	}

	public EmfForeignEntityAttributeValidator<S, T> addPredicate(BiPredicate<S, T> predicate) {

		predicates.add(predicate);
		return this;
	}

	@Override
	public boolean test(S sourceEntity, T targetEntity) {

		// null is allowed by default
		if (targetEntity == null) {
			return true;
		}

		// check target entity is active
		boolean active = targetEntity.table().getEmfTableConfiguration().getDeactivationStrategy().isActive(targetEntity);
		if (!active) {
			return false;
		}

		// check scope
		if (!scope.isVisible(sourceEntity, targetEntity)) {
			return false;
		}

		// check additional predicates
		for (BiPredicate<S, T> predicate: predicates) {
			if (!predicate.test(sourceEntity, targetEntity)) {
				return false;
			}
		}

		return true;
	}
}
