package com.softicar.platform.dom.resource.set;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import java.util.Optional;

/**
 * Provides a mapping from {@link IResourceKey} to the respective
 * {@link IResource}.
 *
 * @author Oliver Richers
 */
public interface IDomResourceSet {

	/**
	 * Returns the {@link IResource} instance for the given
	 * {@link IResourceKey}.
	 *
	 * @param resourceKey
	 *            the {@link IResourceKey} (never <i>null</i>)
	 * @return the respective {@link IResource}
	 */
	Optional<IResource> getResource(IResourceKey resourceKey);
}
