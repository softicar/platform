package com.softicar.platform.emf.module.permission;

import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.permission.statik.AbstractEmfStaticPermissionBuilder;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermissionConfiguration;

/**
 * A builder class for instances of {@link IEmfModulePermission}.
 *
 * @author Oliver Richers
 */
public class EmfModulePermissionBuilder<T extends IEmfModuleInstance<T>> extends AbstractEmfStaticPermissionBuilder<T, IEmfModulePermission<T>, EmfModulePermissionBuilder<T>> {

	@Override
	protected IEmfModulePermission<T> createPermission(IEmfStaticPermissionConfiguration<T> configuration) {

		return new EmfModulePermission<>(configuration);
	}

	@Override
	protected EmfModulePermissionBuilder<T> getThis() {

		return this;
	}
}
