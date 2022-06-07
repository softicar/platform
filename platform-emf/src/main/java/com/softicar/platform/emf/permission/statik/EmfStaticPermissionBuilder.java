package com.softicar.platform.emf.permission.statik;

import com.softicar.platform.emf.permission.IEmfPermissionPredicate;
import java.util.Objects;

public class EmfStaticPermissionBuilder<T> extends AbstractEmfStaticPermissionBuilder<T, IEmfStaticPermission<T>, EmfStaticPermissionBuilder<T>> {

	private final IEmfPermissionPredicate<T> predicate;

	public EmfStaticPermissionBuilder(IEmfPermissionPredicate<T> predicate) {

		this.predicate = Objects.requireNonNull(predicate);
	}

	@Override
	protected IEmfStaticPermission<T> createPermission(IEmfStaticPermissionConfiguration<T> configuration) {

		return new EmfStaticPermission<>(configuration, predicate);
	}

	@Override
	protected EmfStaticPermissionBuilder<T> getThis() {

		return this;
	}
}
