package com.softicar.platform.emf.authorization.role.statik;

import com.softicar.platform.emf.authorization.role.IEmfRolePredicate;
import java.util.Objects;

public class EmfStaticRoleBuilder<T> extends AbstractEmfStaticRoleBuilder<T, IEmfStaticRole<T>, EmfStaticRoleBuilder<T>> {

	private final IEmfRolePredicate<T> predicate;

	public EmfStaticRoleBuilder(IEmfRolePredicate<T> predicate) {

		this.predicate = Objects.requireNonNull(predicate);
	}

	@Override
	protected IEmfStaticRole<T> createRole(IEmfStaticRoleConfiguration<T> configuration) {

		return new EmfStaticRole<>(configuration, predicate);
	}

	@Override
	protected EmfStaticRoleBuilder<T> getThis() {

		return this;
	}
}
