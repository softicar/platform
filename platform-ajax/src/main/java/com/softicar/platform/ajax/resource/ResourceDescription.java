package com.softicar.platform.ajax.resource;

import com.softicar.platform.ajax.customization.IAjaxSettings;
import com.softicar.platform.common.io.resource.IResource;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

class ResourceDescription {

	private final IResource resource;
	private boolean cachable;

	public ResourceDescription(IResource resource) {

		this.resource = resource;
		this.cachable = false;
	}

	public ResourceDescription setCachable(boolean cachable) {

		this.cachable = cachable;
		return this;
	}

	public IResource getResource() {

		return resource;
	}

	public String getCacheControl(IAjaxSettings settings) {

		if (cachable) {
			return "public, max-age=%s, immutable".formatted(settings.getMaximumAgeForCachedResources());
		} else {
			return "no-store";
		}
	}

	public String getContentTypeString() {

		String contentTypeString = Objects//
			.requireNonNull(resource.getMimeType())
			.getIdentifier();

		Optional<Charset> charset = resource.getCharset();
		if (charset.isPresent()) {
			contentTypeString += "; charset=" + charset.get().name();
		}

		return contentTypeString;
	}
}
