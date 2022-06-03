package com.softicar.platform.emf.authorizer;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.form.error.EmfDefaultAccessDeniedDisplayCreator;
import com.softicar.platform.emf.form.error.IEmfAccessDeniedDisplayCreator;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfAuthorizer<R extends IEmfTableRow<R, ?>, S> implements IEmfAuthorizer<R, S> {

	private IEmfPermission<S> creationPermission;
	private IEmfPermission<R> viewPermission;
	private IEmfPermission<R> editPermission;
	private IEmfPermission<R> deletePermission;
	private IEmfAccessDeniedDisplayCreator<R> accessDeniedDisplayCreator;

	public EmfAuthorizer() {

		this.creationPermission = EmfPermissions.anybody();
		this.viewPermission = EmfPermissions.anybody();
		this.editPermission = EmfPermissions.anybody();
		this.deletePermission = EmfPermissions.nobody();
		this.accessDeniedDisplayCreator = new EmfDefaultAccessDeniedDisplayCreator<>();
	}

	@Override
	public void assertIsVisible(R tableRow, IBasicUser user) throws EmfTableRowNotVisibleToUserException {

		if (!viewPermission.test(tableRow, user)) {
			throw new EmfTableRowNotVisibleToUserException();
		}
	}

	@Override
	public IEmfPermission<S> getCreationPermission() {

		return creationPermission;
	}

	@Override
	public IEmfPermission<R> getViewPermission() {

		return viewPermission;
	}

	@Override
	public IEmfPermission<R> getEditPermission() {

		return editPermission;
	}

	@Override
	public IEmfPermission<R> getDeletePermission() {

		return deletePermission;
	}

	@Override
	public IEmfAccessDeniedDisplayCreator<R> getAccessDeniedDisplayCreator() {

		return accessDeniedDisplayCreator;
	}

	public EmfAuthorizer<R, S> setCreationPermission(IEmfPermission<S> creationPermission) {

		this.creationPermission = creationPermission;
		return this;
	}

	public EmfAuthorizer<R, S> setViewPermission(IEmfPermission<R> viewPermission) {

		this.viewPermission = viewPermission;
		return this;
	}

	public EmfAuthorizer<R, S> setEditPermission(IEmfPermission<R> editPermission) {

		this.editPermission = editPermission;
		return this;
	}

	public EmfAuthorizer<R, S> setDeletePermission(IEmfPermission<R> deletePermission) {

		this.deletePermission = deletePermission;
		return this;
	}

	public EmfAuthorizer<R, S> setAccessDeniedDisplayCreator(IEmfAccessDeniedDisplayCreator<R> creator) {

		this.accessDeniedDisplayCreator = creator;
		return this;
	}
}
