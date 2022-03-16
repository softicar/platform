package com.softicar.platform.common.io.resource.hash;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.io.resource.IResource;

/**
 * A hash of the content of an {@link IResource}.
 *
 * @author Oliver Richers
 */
public class ResourceHash {

	private final String hash;

	public ResourceHash(String hash) {

		this.hash = hash.toLowerCase();
	}

	@Override
	public boolean equals(Object object) {

		return CastUtils//
			.tryCast(object, ResourceHash.class)
			.map(other -> other.hash.equals(hash))
			.orElse(false);
	}

	@Override
	public int hashCode() {

		return hash.hashCode();
	}

	@Override
	public String toString() {

		return hash.toString();
	}
}
