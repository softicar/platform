package com.softicar.platform.emf.module.role;

import com.softicar.platform.emf.authorization.role.statik.AbstractEmfStaticRoleBuilder;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRoleConfiguration;
import com.softicar.platform.emf.module.IEmfModuleInstance;

/**
 * A builder class for instances of {@link IEmfModuleRole}.
 *
 * @author Oliver Richers
 */
public class EmfModuleRoleBuilder<T extends IEmfModuleInstance<T>> extends AbstractEmfStaticRoleBuilder<T, IEmfModuleRole<T>, EmfModuleRoleBuilder<T>> {

	@Override
	protected IEmfModuleRole<T> createRole(IEmfStaticRoleConfiguration<T> configuration) {

		return new EmfModuleRole<>(configuration);
	}

	@Override
	protected EmfModuleRoleBuilder<T> getThis() {

		return this;
	}
}
