package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.access.module.AbstractModuleTest;
import java.util.Optional;
import java.util.UUID;
import org.junit.Ignore;
import org.junit.Test;

// FIXME reactivate all tests
public class ModuleInstanceBaseCacheTest extends AbstractModuleTest {

	private static final UUID MODULE_UUID = UUID.fromString("f4595b70-e413-4d4b-bbdb-a080d625c9b3");
	private static final UUID OTHER_MODULE_UUID = UUID.fromString("c57912a8-7e26-48c7-bfbb-f8119b57ead9");
	private final ModuleInstanceBaseCache cache;
	private final AGModuleInstanceBase firstModuleInstanceBase;

	public ModuleInstanceBaseCacheTest() {

		this.cache = new ModuleInstanceBaseCache();
		this.firstModuleInstanceBase = insertModuleInstanceBase(MODULE_UUID);
		insertModuleInstanceBase(MODULE_UUID).setActive(false).save();
	}

	@Test
	public void testIsDeployedWithActiveInstance() {

		assertTrue(cache.isDeployed(MODULE_UUID));
	}

	@Test
	@Ignore
	public void testIsDeployedWithInactiveInstance() {

		assertFalse(cache.isDeployed(MODULE_UUID));
	}

	@Test
	public void testIsDeployedWithOtherModule() {

		assertFalse(cache.isDeployed(OTHER_MODULE_UUID));
	}

	@Test
	public void testGetActiveWithActiveInstance() {

		Optional<AGModuleInstanceBase> moduleInstance = cache.getActive(MODULE_UUID);
		assertTrue(moduleInstance.isPresent());
		assertSame(firstModuleInstanceBase, moduleInstance.get());
	}

	@Test
	@Ignore
	public void testGetActiveWithInactiveInstance() {

		Optional<AGModuleInstanceBase> moduleInstance = cache.getActive(MODULE_UUID);
		assertFalse(moduleInstance.isPresent());
	}

	@Test
	public void testGetActiveWithOtherModule() {

		Optional<AGModuleInstanceBase> moduleInstance = cache.getActive(OTHER_MODULE_UUID);
		assertFalse(moduleInstance.isPresent());
	}
}
