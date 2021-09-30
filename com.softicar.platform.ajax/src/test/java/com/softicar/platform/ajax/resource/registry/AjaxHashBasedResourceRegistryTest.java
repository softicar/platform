package com.softicar.platform.ajax.resource.registry;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AjaxHashBasedResourceRegistryTest extends Assert {

	private static final ResourceHash HASH = new ResourceHash("abc");
	private final AjaxHashBasedResourceRegistry registry;
	private final IResource resource;

	public AjaxHashBasedResourceRegistryTest() {

		this.registry = new AjaxHashBasedResourceRegistry();
		this.resource = Mockito.mock(IResource.class);

		Mockito.when(resource.getContentHash()).thenReturn(Optional.of(HASH));
	}

	@Test
	public void testRegister() {

		Optional<AjaxResourceUrl> url = registry.register(resource);

		assertFalse(url.isEmpty());
		assertEquals("?resourceHash=abc", url.get().toString());
	}

	@Test
	public void testRegisterWithoutContentHash() {

		Mockito.when(resource.getContentHash()).thenReturn(Optional.empty());

		Optional<AjaxResourceUrl> url = registry.register(resource);

		assertTrue(url.isEmpty());
	}

	@Test
	public void testGetResource() {

		registry.register(resource);

		Optional<IResource> returnedResource = registry.getResource(HASH);

		assertTrue(returnedResource.isPresent());
		assertSame(resource, returnedResource.get());
	}

	@Test
	public void testGetResourceWithNonRegisteredResource() {

		Optional<IResource> returnedResource = registry.getResource(HASH);

		assertFalse(returnedResource.isPresent());
	}
}
