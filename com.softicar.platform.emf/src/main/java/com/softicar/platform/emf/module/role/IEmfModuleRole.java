package com.softicar.platform.emf.module.role;

import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.module.IEmfModuleInstance;

/**
 * Represents an {@link IEmfRole} that is scoped on an
 * {@link IEmfModuleInstance}.
 *
 * @author Oliver Richers
 */
public interface IEmfModuleRole<T extends IEmfModuleInstance<T>> extends IEmfStaticRole<T> {

	// nothing
}
