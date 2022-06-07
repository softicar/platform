package com.softicar.platform.emf.permission;

public class EmfPermissionWrapper {

	private final IEmfPermission<?> permission;

	public EmfPermissionWrapper(IEmfPermission<?> permission) {

		this.permission = permission;
	}

	public IEmfPermission<?> getPermission() {

		return permission;
	}
}
