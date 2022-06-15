package com.softicar.platform.core.module.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.permission.statik.AbstractEmfStaticPermission;
import com.softicar.platform.emf.permission.statik.EmfStaticPermissionConfiguration;
import java.util.UUID;

/**
 * Special implementation of {@link IEmfModulePermission} for
 * {@link AGCoreModuleInstance}.
 *
 * @author Oliver Richers
 */
public class CoreModulePermission extends AbstractEmfStaticPermission<AGCoreModuleInstance> implements IEmfModulePermission<AGCoreModuleInstance> {

	public CoreModulePermission(String permissionUuid, IDisplayString title) {

		this(UUID.fromString(permissionUuid), title);
	}

	public CoreModulePermission(UUID permissionUuid, IDisplayString title) {

		super(new EmfStaticPermissionConfiguration<AGCoreModuleInstance>().setUuid(permissionUuid).setTitle(title));
	}

	@Override
	public boolean test(AGCoreModuleInstance moduleInstance, IBasicUser user) {

		return AGUser.get(user).hasModulePermission(this, AGCoreModuleInstance.getInstance().pk());
	}

	public boolean test(IBasicUser user) {

		return test(AGCoreModuleInstance.getInstance(), user);
	}

	/**
	 * Converts this {@link CoreModulePermission} into a permission on an
	 * entity.
	 *
	 * @return the converted permission (never null)
	 */
	public <E> IEmfPermission<E> toOtherEntityPermission() {

		return new ConvertedCoreModulePermission<>(this);
	}
}
