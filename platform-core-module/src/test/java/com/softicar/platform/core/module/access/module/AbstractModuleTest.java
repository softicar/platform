package com.softicar.platform.core.module.access.module;

import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.uuid.AGUuid;
import java.util.UUID;

public abstract class AbstractModuleTest extends AbstractCoreTest {

	protected AGModuleInstanceBase insertModuleInstanceBase(UUID uuid) {

		return new AGModuleInstanceBase()//
			.setActive(true)
			.setModuleUuid(AGUuid.getOrCreate(uuid))
			.save();
	}
}
