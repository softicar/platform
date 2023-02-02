package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.core.module.CoreI18n;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Represents the URL path for an {@link IWebService}.
 *
 * @author Oliver Richers
 */
public class WebServicePath {

	private static final Pattern SERVICE_PATH_REGEX = Pattern.compile("/service/([^/]*)(/.*)?");
	private final String serviceIdentifier;
	private final String resourcePath;

	private WebServicePath(String serviceIdentifier, String resourcePath) {

		this.serviceIdentifier = Objects.requireNonNull(serviceIdentifier);
		this.resourcePath = Objects.requireNonNull(resourcePath);
	}

	/**
	 * Parses the given URL path.
	 *
	 * @param path
	 *            the path (may be <i>null</i>)
	 * @return the optional, parsed {@link WebServicePath}
	 */
	public static Optional<WebServicePath> parse(String path) {

		if (path != null) {
			var matcher = SERVICE_PATH_REGEX.matcher(path);
			if (matcher.matches()) {
				String serviceIdentifier = Optional.ofNullable(matcher.group(1)).orElse("");
				String resourcePath = Optional.ofNullable(matcher.group(2)).orElse("");
				return Optional.of(new WebServicePath(serviceIdentifier, resourcePath));
			}
		}
		return Optional.empty();
	}

	/**
	 * Parses the given URL path and throws an exception on failure.
	 *
	 * @param path
	 *            the path (never <i>null</i>)
	 * @return the parsed {@link WebServicePath}
	 */
	public static WebServicePath parseOrThrow(String path) {

		return parse(path).orElseThrow(() -> new SofticarUserException(CoreI18n.WEB_SERVICE_PATH_IS_ILLEGAL));
	}

	/**
	 * Returns the service identifier (usually a UUID).
	 * <p>
	 * For example, for the path <code>/service/123/some/resource</code> the
	 * service identifier <code>123</code> is returned.
	 *
	 * @return the service identifier (never <i>null</i>)
	 */
	public String getServiceIdentifier() {

		return serviceIdentifier;
	}

	/**
	 * Returns the service resource path, that is, the part after the service
	 * identifier.
	 * <p>
	 * For example, for the path <code>/service/123/some/resource</code> the
	 * resource path <code>/some/resource</code> is returned.
	 *
	 * @return the resource path (never <i>null</i>)
	 */
	public String getResourcePath() {

		return resourcePath;
	}
}
