package com.softicar.platform.common.io.resource;

import java.util.Optional;

/**
 * Common interface of resource registries.
 * <p>
 * A resource registry is primarily used to generate unique resource ids and to
 * provide a mapping between such generated ids and the corresponding resources.
 * The registered resources are held by weak references, so resources not
 * referenced from somewhere else may be collected and removed from the resource
 * registry. Here is some sample code:
 *
 * <pre>
 * <code>
 * // creating a registry //
 * IResourceRegistry registry = createSomeRegistry();
 * assert (registry.getResource(0) == null);
 *
 * // registering a resource //
 * IResource resource = getSomeResource();
 * int resourceId = registry.register(resource);
 * assert (registry.getResource(resourceId) == resource);
 *
 * // re-registering the same resource //
 * int resourceId2 = registry.register(resource);
 * assert (resourceId2 == resourceId);
 * </code>
 * </pre>
 *
 * @author Oliver Richers
 */
public interface IResourceRegistry {

	/**
	 * Returns the registered resource matching the given resource id.
	 * <p>
	 * There are actually two possible reasons, why this method might return an
	 * empty {@link Optional}. Either the matching resource was never registered
	 * or the matching resource was removed by the garbage collector.
	 *
	 * @param resourceId
	 *            the id of the resource to return
	 * @return the matching resource
	 */
	Optional<IResource> getResource(int resourceId);

	/**
	 * Registers a new resource.
	 *
	 * @param resource
	 *            the new resource to register, never null
	 * @return the allocated id for the given resource
	 */
	int register(IResource resource);
}
