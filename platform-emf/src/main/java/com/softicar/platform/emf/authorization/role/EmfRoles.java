package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.emf.EmfI18n;

public class EmfRoles {

	private static final IEmfRole<?> ANYBODY = new EmfRole<>(EmfI18n.ANYBODY, IEmfRolePredicate.always());
	private static final IEmfRole<?> NOBODY = new EmfRole<>(EmfI18n.NOBODY, IEmfRolePredicate.never());

	@SafeVarargs
	public static <E> IEmfRole<E> any(IEmfRole<E>...roles) {

		return new EmfAnyRole<>(roles);
	}

	@SafeVarargs
	public static <E> IEmfRole<E> all(IEmfRole<E>...roles) {

		return new EmfAllRoles<>(roles);
	}

	public static <E> IEmfRole<E> user(ISqlField<E, ? extends IBasicUser> userField) {

		return new EmfUserRole<>(userField);
	}

	@SuppressWarnings("unchecked")
	public static <E> IEmfRole<E> anybody() {

		return (IEmfRole<E>) ANYBODY;
	}

	@SuppressWarnings("unchecked")
	public static <E> IEmfRole<E> nobody() {

		return (IEmfRole<E>) NOBODY;
	}
}
