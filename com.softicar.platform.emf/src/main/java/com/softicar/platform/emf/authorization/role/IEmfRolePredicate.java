package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.user.IBasicUser;
import java.util.function.BiPredicate;

@FunctionalInterface
public interface IEmfRolePredicate<T> extends BiPredicate<T, IBasicUser> {

	static <X> IEmfRolePredicate<X> always() {

		return (tableRow, user) -> true;
	}

	static <X> IEmfRolePredicate<X> never() {

		return (tableRow, user) -> false;
	}

	@Override
	boolean test(T tableRow, IBasicUser user);
}
