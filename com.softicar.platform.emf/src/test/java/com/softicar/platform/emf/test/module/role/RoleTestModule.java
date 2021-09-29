package com.softicar.platform.emf.test.module.role;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.emf.module.AbstractEmfModule;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import java.util.Collection;

@TestingOnly
@EmfSourceCodeReferencePointUuid(RoleTestModule.UUID)
public class RoleTestModule extends AbstractEmfModule<RoleTestModuleInstance> {

	public static final String UUID = "dc60f9f1-e32c-4edd-a521-4c08a27d1eaa";

	@Override
	public Collection<RoleTestModuleInstance> getActiveModuleInstances() {

		return RoleTestModuleInstance.TABLE.loadAll();
	}

	@Override
	public RoleTestModuleInstance getModuleInstanceById(Integer moduleInstanceId) {

		return RoleTestModuleInstance.TABLE.get(moduleInstanceId);
	}
}
