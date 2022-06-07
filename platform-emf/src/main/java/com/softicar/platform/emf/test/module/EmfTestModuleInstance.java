package com.softicar.platform.emf.test.module;

import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import java.util.UUID;

public class EmfTestModuleInstance implements IEmfModuleInstance<EmfTestModuleInstance> {

	private static final EmfTestModuleInstance INSTANCE = new EmfTestModuleInstance();

	public static EmfTestModuleInstance getInstance() {

		return INSTANCE;
	}

	@Override
	public ItemId getItemId() {

		return new ItemId(0);
	}

	@Override
	public IUuid getModuleUuid() {

		return () -> UUID.fromString("03d147d8-1549-45dd-ba06-53978e99111a");
	}

	@Override
	public boolean hasPermission(IEmfModulePermission<EmfTestModuleInstance> permission, IBasicUser user) {

		throw new UnsupportedOperationException();
	}
}
