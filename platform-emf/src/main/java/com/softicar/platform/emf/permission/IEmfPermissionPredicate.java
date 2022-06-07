package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.user.IBasicUser;
import java.util.function.BiPredicate;

@FunctionalInterface
public interface IEmfPermissionPredicate<T> extends BiPredicate<T, IBasicUser> {

	static <X> IEmfPermissionPredicate<X> always() {

		return (tableRow, user) -> true;
	}

	static <X> IEmfPermissionPredicate<X> never() {

		return (tableRow, user) -> false;
	}

	@Override
	boolean test(T tableRow, IBasicUser user);
}
