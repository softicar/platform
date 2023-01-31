package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import java.util.Optional;
import java.util.regex.Pattern;

public class WebServicePath {

	private static final Pattern SERVICE_PATH_REGEX = Pattern.compile("/service/([^/]*)(/.*)");
	private final String serviceIdentifier;
	private final String resourcePath;

	private WebServicePath(String serviceIdentifier, String resourcePath) {

		this.serviceIdentifier = serviceIdentifier;
		this.resourcePath = resourcePath;
	}

	public static Optional<WebServicePath> parse(String path) {

		if (path != null) {
			var matcher = SERVICE_PATH_REGEX.matcher(path);
			if (matcher.matches()) {
				return Optional.of(new WebServicePath(matcher.group(1), matcher.group(2)));
			}
		}
		return Optional.empty();
	}

	public static WebServicePath parseOrThrow(String path) {

		return parse(path).orElseThrow(() -> new SofticarUserException(CoreI18n.WEB_SERVICE_PATH_IS_ILLEGAL));
	}

	public String getServiceIdentifier() {

		return serviceIdentifier;
	}

	public String getResourcePath() {

		return resourcePath;
	}

	@Override
	public String toString() {

		return "[" + serviceIdentifier + "," + resourcePath + "]";
	}
}
