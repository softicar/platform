package com.softicar.platform.common.io.resource.hash;

import com.softicar.platform.common.io.resource.IResource;
import java.util.Optional;

/**
 * Abstract implementation of {@link IResource#getContentHash()}.
 * <p>
 * The computation of the {@link IResource} content hash is done only on demand
 * and then cached for consecutive calls.
 *
 * @author Oliver Richers
 */
public abstract class AbstractHashableResource implements IResource {

	private final LazyResourceHasher hasher;

	public AbstractHashableResource() {

		this.hasher = new LazyResourceHasher(this);
	}

	@Override
	public Optional<ResourceHash> getContentHash() {

		return Optional.of(hasher.getHash());
	}
}
