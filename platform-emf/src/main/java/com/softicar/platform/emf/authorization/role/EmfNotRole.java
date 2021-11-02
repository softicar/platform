package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.EmfI18n;

/**
 * Negates a given role.
 * <p>
 * This role applies to a user if the given sub-role does not apply.
 *
 * @author Oliver Richers
 */
public class EmfNotRole<R> implements IEmfRole<R> {

	private final IEmfRole<R> role;

	public EmfNotRole(IEmfRole<R> role) {

		this.role = role;
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.NOT_ARG1.toDisplay(role.getTitle());
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		return !role.test(tableRow, user);
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}

	@Override
	public void accept(IEmfRoleVisitor<R> visitor) {

		visitor.visitNot(role);
	}
}
