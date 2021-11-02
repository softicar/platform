package com.softicar.platform.emf.module.role;

import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.role.statik.AbstractEmfStaticRole;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRoleConfiguration;
import com.softicar.platform.emf.module.IEmfModuleInstance;

/**
 * Default implementation of {@link IEmfModuleRole}.
 *
 * @author Oliver Richers
 */
class EmfModuleRole<T extends IEmfModuleInstance<T>> extends AbstractEmfStaticRole<T> implements IEmfModuleRole<T> {

	public EmfModuleRole(IEmfStaticRoleConfiguration<T> configuration) {

		super(configuration);
	}

	@Override
	public boolean test(T moduleInstance, IBasicUser user) {

		return testRole(moduleInstance, user) || testInheritedRoles(moduleInstance, user);
	}

	private boolean testRole(T moduleInstance, IBasicUser user) {

		return moduleInstance.hasRole(this, user);
	}
}
