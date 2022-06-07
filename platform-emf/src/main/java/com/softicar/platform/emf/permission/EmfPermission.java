package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;

public class EmfPermission<R> extends AbstractEmfPermission<R> {

	private final IDisplayString title;
	private final IEmfPermissionPredicate<R> predicate;

	public EmfPermission(IDisplayString title, IEmfPermissionPredicate<R> predicate) {

		this.title = title;
		this.predicate = predicate;
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public boolean test(R tableRow, IBasicUser user) {

		try {
			return tableRow != null && predicate.test(tableRow, user);
		} catch (Exception exception) {
			logEvaluationException(exception);
			return false;
		}
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}
}
