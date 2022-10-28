package com.softicar.platform.ajax.resource;

import com.softicar.platform.common.io.resource.hash.ResourceHash;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Extracts resource hash and resource ID from resource URLs.
 *
 * @author Oliver Richers
 */
public class AjaxResourceUrlParser {

	private static final Pattern HASH_PATTERN = Pattern.compile(".*\\?resourceHash=([0-9a-fA-F]+).*");
	private static final Pattern ID_PATTERN = Pattern.compile(".*\\?resourceId=([0-9]+).*");
	private final String resourceUrl;

	public AjaxResourceUrlParser(String resourceUrl) {

		this.resourceUrl = resourceUrl;
	}

	public Optional<ResourceHash> getResourceHash() {

		var matcher = HASH_PATTERN.matcher(resourceUrl);
		if (matcher.matches()) {
			return Optional.of(new ResourceHash(matcher.group(1)));
		} else {
			return Optional.empty();
		}
	}

	public Optional<Integer> getResourceId() {

		var matcher = ID_PATTERN.matcher(resourceUrl);
		if (matcher.matches()) {
			return Optional.of(Integer.valueOf(matcher.group(1)));
		} else {
			return Optional.empty();
		}
	}
}
