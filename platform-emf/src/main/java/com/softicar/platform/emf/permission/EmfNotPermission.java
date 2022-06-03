package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.EmfI18n;

/**
 * Negates a given {@link IEmfPermission}.
 * <p>
 * This {@link IEmfPermission} applies to a user if the wrapped
 * {@link IEmfPermission} does not apply.
 *
 * @author Oliver Richers
 */
public class EmfNotPermission<R> implements IEmfPermission<R> {

	private final IEmfPermission<R> permission;

	public EmfNotPermission(IEmfPermission<R> permission) {

		this.permission = permission;
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.NOT_ARG1.toDisplay(permission.getTitle());
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return !permission.test(tableRow, user);
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}

	@Override
	public void accept(IEmfPermissionVisitor<R> visitor) {

		visitor.visitNot(permission);
	}
}
