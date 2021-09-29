package com.softicar.platform.common.io.resource.hash;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.string.hash.Hash;
import com.softicar.platform.common.string.hexadecimal.Hexadecimal;

/**
 * Computes the hash of an {@link IResource} on demand and only once.
 *
 * @author Oliver Richers
 */
class LazyResourceHasher {

	private final IResource resource;
	private volatile ResourceHash hash;

	public LazyResourceHasher(IResource resource) {

		this.resource = resource;
		this.hash = null;
	}

	public ResourceHash getHash() {

		if (hash == null) {
			this.hash = computeHash();
		}
		return hash;
	}

	private ResourceHash computeHash() {

		synchronized (resource) {
			return new ResourceHash(Hexadecimal.getHexStringLC(Hash.SHA1.getHash(resource::getResourceAsStream)));
		}
	}
}
