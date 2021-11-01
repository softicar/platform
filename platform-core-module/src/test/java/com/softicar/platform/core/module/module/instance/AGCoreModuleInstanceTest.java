package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.validation.EmfValidationException;
import org.junit.Test;

public class AGCoreModuleInstanceTest extends AbstractDbTest {

	@Test
	public void testGetPortalUrl() {

		AGCoreModuleInstance//
			.getInstance()
			.setPortalProtocol("foo")
			.setPortalHost("bar")
			.save();

		assertEquals("foo://bar", AGCoreModuleInstance.getInstance().getPortalUrl());
	}

	@Test
	public void testGetPortalApplicationPath() {

		AGCoreModuleInstance//
			.getInstance()
			.setPortalApplication("foo")
			.save();

		assertEquals("/foo/", AGCoreModuleInstance.getInstance().getPortalApplicationPath());
	}

	@Test(expected = EmfValidationException.class)
	public void testWithEmptyPortalProtocol() {

		AGCoreModuleInstance//
			.getInstance()
			.setPortalProtocol("")
			.save();
	}

	@Test(expected = EmfValidationException.class)
	public void testWithEmptyPortalHost() {

		AGCoreModuleInstance//
			.getInstance()
			.setPortalHost("")
			.save();
	}

	@Test(expected = EmfValidationException.class)
	public void testWithEmptyPortalApplication() {

		AGCoreModuleInstance//
			.getInstance()
			.setPortalApplication("")
			.save();
	}
}
