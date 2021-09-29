package com.softicar.platform.emf.authorization.role.statik;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.role.IEmfRolePredicate;

class EmfStaticRole<T> extends AbstractEmfStaticRole<T> {

	private final IEmfRolePredicate<T> predicate;

	public EmfStaticRole(IEmfStaticRoleConfiguration<T> configuration, IEmfRolePredicate<T> predicate) {

		super(configuration);
		this.predicate = predicate;
	}

	@Override
	public boolean test(T tableRow, IBasicUser user) {

		return testRole(tableRow, user) || testInheritedRoles(tableRow, user);
	}

	private boolean testRole(T tableRow, IBasicUser user) {

		return predicate.test(tableRow, user);
	}
}
