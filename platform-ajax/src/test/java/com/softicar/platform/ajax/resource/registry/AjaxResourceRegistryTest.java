package com.softicar.platform.ajax.resource.registry;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class AjaxResourceRegistryTest extends AbstractTest {

	private static final ResourceHash HASH = new ResourceHash("abc");
	private final AjaxResourceRegistry registry;
	private final IResource resourceWithHash;
	private final IResource resourceWithoutHash;

	public AjaxResourceRegistryTest() {

		this.registry = new AjaxResourceRegistry();
		this.resourceWithHash = Mockito.mock(IResource.class);
		this.resourceWithoutHash = Mockito.mock(IResource.class);

		Mockito.when(resourceWithHash.getContentHash()).thenReturn(Optional.of(HASH));
	}

	@Test
	public void testRegister() {

		AjaxResourceUrl urlWithHash = registry.register(resourceWithHash);
		AjaxResourceUrl urlWithoutHash = registry.register(resourceWithoutHash);

		assertEquals("?resourceHash=abc", urlWithHash.toString());
		assertEquals("?resourceId=1", urlWithoutHash.toString());
	}

	@Test
	public void testGetResource() {

		registry.register(resourceWithHash);
		registry.register(resourceWithoutHash);

		assertSame(resourceWithHash, registry.getResourceByHash(HASH).get());
		assertSame(resourceWithoutHash, registry.getResourceById(1).get());
	}
}
