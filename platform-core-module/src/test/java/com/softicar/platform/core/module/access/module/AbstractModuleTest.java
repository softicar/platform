package com.softicar.platform.core.module.access.module;

import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.util.UUID;

public abstract class AbstractModuleTest extends AbstractCoreTest {

	protected AGModuleInstance insertModuleInstance(UUID uuid) {

		return new AGModuleInstance()//
			.setActive(true)
			.setModuleUuid(AGUuid.getOrCreate(uuid))
			.save();
	}
}
