package com.softicar.platform.common.network.url;

import java.util.Map;
import java.util.Objects;

/**
 * A builder for {@link Url}.
 *
 * @author Oliver Richers
 */
public class UrlBuilder {

	private final Url url;

	public UrlBuilder() {

		this.url = new Url();
	}

	public UrlBuilder setScheme(String scheme) {

		this.url.scheme = scheme;
		return this;
	}

	public UrlBuilder setDomainName(String domainName) {

		this.url.domainName = domainName;
		return this;
	}

	public UrlBuilder setPort(String port) {

		this.url.port = port;
		return this;
	}

	public UrlBuilder setPath(String path) {

		this.url.path = path;
		return this;
	}

	public UrlBuilder addParameter(String name, String value) {

		this.url.parameters.addToList(name, value);
		return this;
	}

	public UrlBuilder addParameters(Map<String, String> parameters) {

		parameters//
			.entrySet()
			.forEach(entry -> this.addParameter(entry.getKey(), entry.getValue()));
		return this;
	}

	public UrlBuilder setFragment(String fragment) {

		this.url.fragment = fragment;
		return this;
	}

	public Url build() {

		Objects.requireNonNull(url.scheme, "Scheme of URL not specified.");
		Objects.requireNonNull(url.domainName, "Domain of URL not specified.");

		return url;
	}
}
