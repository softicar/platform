package com.softicar.platform.core.module.module.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.role.statik.AbstractEmfStaticRole;
import com.softicar.platform.emf.authorization.role.statik.EmfStaticRoleConfiguration;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.UUID;

public class TestModuleRole<I extends IEmfModuleInstance<I>> extends AbstractEmfStaticRole<I> implements IEmfModuleRole<I> {

	public TestModuleRole(String uuid, String title) {

		this(UUID.fromString(uuid), title);
	}

	public TestModuleRole(UUID uuid, String title) {

		super(new EmfStaticRoleConfiguration<I>().setUuid(uuid).setTitle(IDisplayString.create(title)));
	}

	@Override
	public boolean test(I moduleInstance, IBasicUser user) {

		return moduleInstance.hasRole(this, user);
	}
}
