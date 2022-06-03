package com.softicar.platform.core.module.module.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.statik.AbstractEmfStaticPermission;
import com.softicar.platform.emf.permission.statik.EmfStaticPermissionConfiguration;
import java.util.UUID;

public class TestModulePermission<I extends IEmfModuleInstance<I>> extends AbstractEmfStaticPermission<I> implements IEmfModulePermission<I> {

	public TestModulePermission(String uuid, String title) {

		this(UUID.fromString(uuid), title);
	}

	public TestModulePermission(UUID uuid, String title) {

		super(new EmfStaticPermissionConfiguration<I>().setUuid(uuid).setTitle(IDisplayString.create(title)));
	}

	@Override
	public boolean test(I moduleInstance, IBasicUser user) {

		return moduleInstance.hasPermission(this, user);
	}
}
