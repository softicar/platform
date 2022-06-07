package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.sql.field.ISqlField;
import com.softicar.platform.emf.EmfI18n;

public class EmfPermissions {

	private static final IEmfPermission<?> ALWAYS = new EmfPermission<>(EmfI18n.ALWAYS, IEmfPermissionPredicate.always());
	private static final IEmfPermission<?> NEVER = new EmfPermission<>(EmfI18n.NEVER, IEmfPermissionPredicate.never());

	@SafeVarargs
	public static <E> IEmfPermission<E> any(IEmfPermission<E>...permissions) {

		return new EmfAnyPermission<>(permissions);
	}

	@SafeVarargs
	public static <E> IEmfPermission<E> all(IEmfPermission<E>...permissions) {

		return new EmfAllPermissions<>(permissions);
	}

	public static <E> IEmfPermission<E> user(ISqlField<E, ? extends IBasicUser> userField) {

		return new EmfUserPermission<>(userField);
	}

	@SuppressWarnings("unchecked")
	public static <E> IEmfPermission<E> always() {

		return (IEmfPermission<E>) ALWAYS;
	}

	@SuppressWarnings("unchecked")
	public static <E> IEmfPermission<E> never() {

		return (IEmfPermission<E>) NEVER;
	}
}
