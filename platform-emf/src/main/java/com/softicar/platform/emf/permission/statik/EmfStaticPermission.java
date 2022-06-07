package com.softicar.platform.emf.permission.statik;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.permission.IEmfPermissionPredicate;

class EmfStaticPermission<T> extends AbstractEmfStaticPermission<T> {

	private final IEmfPermissionPredicate<T> predicate;

	public EmfStaticPermission(IEmfStaticPermissionConfiguration<T> configuration, IEmfPermissionPredicate<T> predicate) {

		super(configuration);
		this.predicate = predicate;
	}

	@Override
	public boolean test(T tableRow, IBasicUser user) {

		return testPermission(tableRow, user) || testInheritedPermissions(tableRow, user);
	}

	private boolean testPermission(T tableRow, IBasicUser user) {

		return predicate.test(tableRow, user);
	}
}
