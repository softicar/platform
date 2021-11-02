package com.softicar.platform.ajax.resource.registry;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.common.io.resource.IResource;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AjaxIdBasedResourceRegistryTest extends Assert {

	private final AjaxIdBasedResourceRegistry registry;
	private final IResource resource1;
	private final IResource resource2;

	public AjaxIdBasedResourceRegistryTest() {

		this.registry = new AjaxIdBasedResourceRegistry();
		this.resource1 = Mockito.mock(IResource.class);
		this.resource2 = Mockito.mock(IResource.class);
	}

	@Test
	public void testRegister() {

		AjaxResourceUrl url1 = registry.register(resource1);
		AjaxResourceUrl url2 = registry.register(resource2);

		assertEquals("?resourceId=1", url1.toString());
		assertEquals("?resourceId=2", url2.toString());
	}

	@Test
	public void testRegisterWithMultipleCalls() {

		AjaxResourceUrl url1 = registry.register(resource1);
		AjaxResourceUrl url2 = registry.register(resource1);

		assertEquals("?resourceId=1", url1.toString());
		assertEquals("?resourceId=1", url2.toString());
	}

	@Test
	public void testGetResource() {

		registry.register(resource1);
		registry.register(resource2);

		Optional<IResource> returnedResource1 = registry.getResource(1);
		Optional<IResource> returnedResource2 = registry.getResource(2);

		assertTrue(returnedResource1.isPresent());
		assertTrue(returnedResource2.isPresent());
		assertSame(resource1, returnedResource1.get());
		assertSame(resource2, returnedResource2.get());
	}

	@Test
	public void testGetResourceWithNonRegisteredResource() {

		Optional<IResource> returnedResource = registry.getResource(1);

		assertFalse(returnedResource.isPresent());
	}
}
