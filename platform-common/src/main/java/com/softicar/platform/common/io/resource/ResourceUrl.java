package com.softicar.platform.common.io.resource;

import com.softicar.platform.common.core.utils.equals.Equals;

/**
 * Very basic implementation of {@link IResourceUrl}.
 *
 * @author Oliver Richers
 */
public class ResourceUrl implements IResourceUrl {

	private final String urlString;

	public ResourceUrl(String urlString) {

		this.urlString = urlString;
	}

	public ResourceUrl concat(String suffix) {

		return new ResourceUrl(urlString + suffix);
	}

	@Override
	public String toString() {

		return urlString;
	}

	@Override
	public int compareTo(IResourceUrl other) {

		return urlString.compareTo(other.toString());
	}

	@Override
	public int hashCode() {

		return urlString.hashCode();
	}

	@Override
	public boolean equals(Object object) {

		return Equals//
			.comparing((ResourceUrl url) -> url.urlString)
			.compareToObject(this, object);
	}
}
