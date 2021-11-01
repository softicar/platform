package com.softicar.platform.emf.authorizer;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.form.error.EmfDefaultAccessDeniedDisplayCreator;
import com.softicar.platform.emf.form.error.IEmfAccessDeniedDisplayCreator;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfAuthorizer<R extends IEmfTableRow<R, ?>, S> implements IEmfAuthorizer<R, S> {

	private IEmfRole<S> creationRole;
	private IEmfRole<R> viewRole;
	private IEmfRole<R> editRole;
	private IEmfRole<R> deleteRole;
	private IEmfAccessDeniedDisplayCreator<R> accessDeniedDisplayCreator;

	public EmfAuthorizer() {

		this.creationRole = EmfRoles.anybody();
		this.viewRole = EmfRoles.anybody();
		this.editRole = EmfRoles.anybody();
		this.deleteRole = EmfRoles.nobody();
		this.accessDeniedDisplayCreator = new EmfDefaultAccessDeniedDisplayCreator<>();
	}

	@Override
	public void assertIsVisible(R tableRow, IBasicUser user) throws EmfTableRowNotVisibleToUserException {

		if (!viewRole.test(tableRow, user)) {
			throw new EmfTableRowNotVisibleToUserException();
		}
	}

	@Override
	public IEmfRole<S> getCreationRole() {

		return creationRole;
	}

	@Override
	public IEmfRole<R> getViewRole() {

		return viewRole;
	}

	@Override
	public IEmfRole<R> getEditRole() {

		return editRole;
	}

	@Override
	public IEmfRole<R> getDeleteRole() {

		return deleteRole;
	}

	@Override
	public IEmfAccessDeniedDisplayCreator<R> getAccessDeniedDisplayCreator() {

		return accessDeniedDisplayCreator;
	}

	public EmfAuthorizer<R, S> setCreationRole(IEmfRole<S> creationRole) {

		this.creationRole = creationRole;
		return this;
	}

	public EmfAuthorizer<R, S> setViewRole(IEmfRole<R> viewRole) {

		this.viewRole = viewRole;
		return this;
	}

	public EmfAuthorizer<R, S> setEditRole(IEmfRole<R> editRole) {

		this.editRole = editRole;
		return this;
	}

	public EmfAuthorizer<R, S> setDeleteRole(IEmfRole<R> deleteRole) {

		this.deleteRole = deleteRole;
		return this;
	}

	public EmfAuthorizer<R, S> setAccessDeniedDisplayCreator(IEmfAccessDeniedDisplayCreator<R> creator) {

		this.accessDeniedDisplayCreator = creator;
		return this;
	}
}
