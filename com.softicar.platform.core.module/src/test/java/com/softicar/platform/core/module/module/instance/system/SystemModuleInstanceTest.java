package com.softicar.platform.core.module.module.instance.system;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.core.module.module.AbstractSystemModule;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import org.junit.Test;

public class SystemModuleInstanceTest extends AbstractDbTest {

	private final SystemModuleInstance systemModuleInstance;

	public SystemModuleInstanceTest() {

		this.systemModuleInstance = new SystemModuleInstance(TestModule.class);
	}

	@Test
	public void testGetItemId() {

		ItemId itemId = systemModuleInstance.getItemId();

		assertEquals(new ItemId(0), itemId);
	}

	@Test
	public void testGetModuleUuid() {

		IUuid uuid = systemModuleInstance.getModuleUuid();

		assertEquals(EmfSourceCodeReferencePoints.getUuidOrThrow(TestModule.class), uuid.getUuid());
	}

	@TestingOnly
	@EmfSourceCodeReferencePointUuid("7370db05-1393-4dd6-af33-f6803e548539")
	private static class TestModule extends AbstractSystemModule {

		@Override
		protected SystemModuleInstance getSystemModuleInstance() {

			return new SystemModuleInstance(TestModule.class);
		}
	}
}
