package com.softicar.platform.emf.authorizer;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.error.IEmfAccessDeniedDisplayCreator;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public interface IEmfAuthorizer<R extends IEmfTableRow<R, ?>, S> {

	void assertIsVisible(R tableRow, IBasicUser user) throws EmfTableRowNotVisibleToUserException;

	IEmfRole<S> getCreationRole();

	IEmfRole<R> getViewRole();

	IEmfRole<R> getEditRole();

	default IEmfRole<R> getDeleteRole() {

		return EmfRoles.nobody();
	}

	IEmfAccessDeniedDisplayCreator<R> getAccessDeniedDisplayCreator();
}
