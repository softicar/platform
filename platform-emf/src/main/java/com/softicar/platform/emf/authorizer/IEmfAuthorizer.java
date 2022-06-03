package com.softicar.platform.emf.authorizer;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.form.error.IEmfAccessDeniedDisplayCreator;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfAuthorizer<R extends IEmfTableRow<R, ?>, S> {

	void assertIsVisible(R tableRow, IBasicUser user) throws EmfTableRowNotVisibleToUserException;

	IEmfPermission<S> getCreationPermission();

	IEmfPermission<R> getViewPermission();

	IEmfPermission<R> getEditPermission();

	default IEmfPermission<R> getDeletePermission() {

		return EmfPermissions.nobody();
	}

	IEmfAccessDeniedDisplayCreator<R> getAccessDeniedDisplayCreator();
}
