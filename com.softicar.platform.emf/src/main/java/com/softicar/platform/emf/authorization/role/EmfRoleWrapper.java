package com.softicar.platform.emf.authorization.role;

public class EmfRoleWrapper {

	private final IEmfRole<?> role;

	public EmfRoleWrapper(IEmfRole<?> role) {

		this.role = role;
	}

	public IEmfRole<?> getRole() {

		return role;
	}
}
