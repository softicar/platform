package com.softicar.platform.common.io.resource.registry;

import static org.mockito.Mockito.mock;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Before;
import org.junit.Test;

public class ResourceRegistryTest extends AbstractTest {

	private ResourceRegistry resourceRegistry;
	private IResource resource1;
	private IResource resource2;
	private int resourceId1;
	private int resourceId2;

	@Before
	public void before() {

		resourceRegistry = new ResourceRegistry();
		resource1 = mock(IResource.class);
		resource2 = mock(IResource.class);

		resourceId1 = resourceRegistry.register(resource1);
		resourceId2 = resourceRegistry.register(resource2);
	}

	@Test
	public void allocatesResourceIds() {

		assertEquals(1, resourceId1);
		assertEquals(2, resourceId2);
	}

	@Test
	public void reusesResourceIds() {

		assertEquals(resourceId1, resourceRegistry.register(resource1));
		assertEquals(resourceId2, resourceRegistry.register(resource2));
	}

	@Test
	public void returnsCorrectResourceById() {

		assertSame(resource1, resourceRegistry.getResource(resourceId1).get());
		assertSame(resource2, resourceRegistry.getResource(resourceId2).get());
	}

	@Test
	public void holdsResourcesByWeakReferences() {

		// remove the only "hard" reference to the resource
		assertNotNull(resourceRegistry.getResource(resourceId2));
		assertSame(resource2, resourceRegistry.getResource(resourceId2).get());
		resource2 = null;

		// loop until resource has been collected
		for (int i = 0; i < 10; ++i) {
			if (resourceRegistry.getResource(resourceId2).isEmpty()) {
				break;
			}

			System.gc();
			Sleep.sleep(10);
		}

		assertFalse(resourceRegistry.getResource(resourceId2).isPresent());
		assertTrue(resourceRegistry.getResource(resourceId1).isPresent());
	}
}
