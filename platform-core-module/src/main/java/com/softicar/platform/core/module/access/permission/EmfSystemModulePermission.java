package com.softicar.platform.core.module.access.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.access.permission.assignment.module.system.AGSystemModulePermissionAssignment;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.permission.statik.AbstractEmfStaticPermission;
import com.softicar.platform.emf.permission.statik.EmfStaticPermissionConfiguration;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import java.util.UUID;

/**
 * Implementation of {@link IEmfStaticPermission} based on
 * {@link AGSystemModulePermissionAssignment}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfSystemModulePermission extends AbstractEmfStaticPermission<SystemModuleInstance> implements IEmfModulePermission<SystemModuleInstance> {

	public EmfSystemModulePermission(String permissionUuid, IDisplayString title) {

		this(UUID.fromString(permissionUuid), title);
	}

	public EmfSystemModulePermission(UUID permissionUuid, IDisplayString title) {

		super(new EmfStaticPermissionConfiguration<SystemModuleInstance>().setUuid(permissionUuid).setTitle(title));
	}

	@Override
	public boolean test(SystemModuleInstance moduleInstance, IBasicUser user) {

		return AGUser.get(user).hasModulePermission(this);
	}

	/**
	 * Converts this {@link EmfSystemModulePermission} into a permission on an
	 * entity.
	 *
	 * @return the converted permission (never null)
	 */
	public <E> IEmfPermission<E> toOtherEntityPermission() {

		return new EmfConvertedModulePermission<>(this);
	}
}
