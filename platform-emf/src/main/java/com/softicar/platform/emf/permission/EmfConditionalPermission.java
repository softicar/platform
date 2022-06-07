package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.predicate.IEmfPredicate;

public class EmfConditionalPermission<R> extends AbstractEmfPermission<R> {

	private final IEmfPermission<R> permission;
	private final IEmfPredicate<R> predicate;

	public EmfConditionalPermission(IEmfPermission<R> permission, IEmfPredicate<R> predicate) {

		this.permission = permission;
		this.predicate = predicate;
	}

	public IEmfPermission<R> getPermission() {

		return permission;
	}

	public IEmfPredicate<R> getPredicate() {

		return predicate;
	}

	@Override
	public IDisplayString getTitle() {

		return permission.getTitle();
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		try {
			return predicate.test(tableRow) && permission.test(tableRow, user);
		} catch (Exception exception) {
			logEvaluationException(exception);
			return false;
		}
	}

	@Override
	public String toString() {

		return getTitle().toString() + " [" + predicate.getTitle().toString() + "]";
	}

	@Override
	public void accept(IEmfPermissionVisitor<R> visitor) {

		visitor.visitConditional(permission, predicate);
	}
}
