package com.softicar.platform.emf.test.module.permission;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.emf.module.AbstractEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Collection;
import java.util.Optional;

@TestingOnly
@EmfSourceCodeReferencePointUuid(PermissionTestModule.UUID)
public class PermissionTestModule extends AbstractEmfModule<PermissionTestModuleInstance> {

	public static final String UUID = "dc60f9f1-e32c-4edd-a521-4c08a27d1eaa";

	@Override
	public Collection<PermissionTestModuleInstance> getActiveModuleInstances() {

		return PermissionTestModuleInstance.TABLE.loadAll();
	}

	@Override
	public Optional<PermissionTestModuleInstance> getModuleInstance(Integer moduleInstanceId) {

		return Optional.of(PermissionTestModuleInstance.TABLE.get(moduleInstanceId));
	}
}
