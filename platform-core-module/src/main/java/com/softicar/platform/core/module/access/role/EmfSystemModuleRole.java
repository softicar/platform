package com.softicar.platform.core.module.access.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.access.role.assignment.module.system.AGSystemModuleRoleAssignment;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.authorization.role.statik.AbstractEmfStaticRole;
import com.softicar.platform.emf.authorization.role.statik.EmfStaticRoleConfiguration;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.UUID;

/**
 * Implementation of {@link IEmfStaticRole} based on {@link AGSystemModuleRoleAssignment}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmfSystemModuleRole extends AbstractEmfStaticRole<SystemModuleInstance> implements IEmfModuleRole<SystemModuleInstance> {

	public EmfSystemModuleRole(String roleUuid, IDisplayString title) {

		this(UUID.fromString(roleUuid), title);
	}

	public EmfSystemModuleRole(UUID roleUuid, IDisplayString title) {

		super(new EmfStaticRoleConfiguration<SystemModuleInstance>().setUuid(roleUuid).setTitle(title));
	}

	@Override
	public boolean test(SystemModuleInstance moduleInstance, IBasicUser user) {

		return AGUser.get(user).hasModuleRole(this);
	}

	/**
	 * Converts this {@link EmfSystemModuleRole} into a role on an entity.
	 *
	 * @return the converted role (never null)
	 */
	public <E> IEmfRole<E> toOtherEntityRole() {

		return new EmfConvertedModuleRole<>(this);
	}
}
