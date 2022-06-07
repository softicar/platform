package com.softicar.platform.emf.module.permission;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.permission.statik.AbstractEmfStaticPermission;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermissionConfiguration;

/**
 * Default implementation of {@link IEmfModulePermission}.
 *
 * @author Oliver Richers
 */
class EmfModulePermission<T extends IEmfModuleInstance<T>> extends AbstractEmfStaticPermission<T> implements IEmfModulePermission<T> {

	public EmfModulePermission(IEmfStaticPermissionConfiguration<T> configuration) {

		super(configuration);
	}

	@Override
	public boolean test(T moduleInstance, IBasicUser user) {

		return testPermission(moduleInstance, user) || testInheritedPermissions(moduleInstance, user);
	}

	private boolean testPermission(T moduleInstance, IBasicUser user) {

		return moduleInstance.hasPermission(this, user);
	}
}
