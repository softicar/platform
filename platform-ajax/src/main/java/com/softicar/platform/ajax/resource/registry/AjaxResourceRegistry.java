package com.softicar.platform.ajax.resource.registry;

import com.softicar.platform.ajax.resource.AjaxResourceUrl;
import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;

public class AjaxResourceRegistry {

	private final AjaxHashBasedResourceRegistry hashBasedRegistry;
	private final AjaxIdBasedResourceRegistry idBasedRegistry;

	public AjaxResourceRegistry() {

		this.hashBasedRegistry = new AjaxHashBasedResourceRegistry();
		this.idBasedRegistry = new AjaxIdBasedResourceRegistry();
	}

	public static AjaxResourceRegistry getInstance(HttpSession session) {

		return new AjaxSessionAttributeManager(session)//
			.getOrAddInstance(AjaxResourceRegistry.class, AjaxResourceRegistry::new);
	}

	/**
	 * Generates an {@link AjaxResourceUrl} to access the specified
	 * {@link IResource}.
	 * <p>
	 * Depending on the {@link IResource}, this either returns an
	 * {@link AjaxResourceUrl} based on the {@link IResource} content hash, or
	 * on the identity of the {@link IResource}.
	 *
	 * @param resource
	 *            the {@link IResource} to generate an URL for (never
	 *            <i>null</i>)
	 * @return a URL to access the specified {@link IResource} (never
	 *         <i>null</i>)
	 */
	public AjaxResourceUrl register(IResource resource) {

		return hashBasedRegistry.register(resource).orElseGet(() -> idBasedRegistry.register(resource));
	}

	/**
	 * Returns the {@link IResource} identified by the given ID.
	 *
	 * @param resourceId
	 *            the ID of the {@link IResource}
	 * @return the matching {@link IResource} as {@link Optional}
	 */
	public Optional<IResource> getResourceById(int resourceId) {

		return idBasedRegistry.getResource(resourceId);
	}

	/**
	 * Returns the {@link IResource} identified by the given
	 * {@link ResourceHash}.
	 *
	 * @param resourceHash
	 *            the {@link ResourceHash} of the {@link IResource} (never
	 *            <i>null</i>)
	 * @return the matching {@link IResource} as {@link Optional}
	 */
	public Optional<IResource> getResourceByHash(ResourceHash resourceHash) {

		return hashBasedRegistry.getResource(resourceHash);
	}
}
