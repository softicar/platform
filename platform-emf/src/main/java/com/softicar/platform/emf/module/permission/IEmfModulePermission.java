package com.softicar.platform.emf.module.permission;

import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;

/**
 * Represents an {@link IEmfPermission} that is scoped on an
 * {@link IEmfModuleInstance}.
 *
 * @author Oliver Richers
 */
public interface IEmfModulePermission<T extends IEmfModuleInstance<T>> extends IEmfStaticPermission<T> {

	// nothing
}
