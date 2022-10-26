package com.softicar.platform.common.network.url;

import com.softicar.platform.common.container.map.list.IListMap;
import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.io.network.UrlCoding;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * A simple URL class.
 * <p>
 * Use {@link UrlBuilder} to create a new instance of this class.
 *
 * @author Oliver Richers
 */
public class Url {

	protected String scheme;
	protected String domainName;
	protected String port;
	protected String path;
	protected IListMap<String, String> parameters;
	protected String fragment;

	protected Url() {

		this.parameters = new ListTreeMap<>();
	}

	public String getScheme() {

		return scheme;
	}

	public String getDomainName() {

		return domainName;
	}

	public String getPort() {

		return port;
	}

	public String getPath() {

		return path;
	}

	public String getQueryString() {

		return parameters//
			.entrySet()
			.stream()
			.map(Url::convertToQueryString)
			.collect(Collectors.joining("&"));
	}

	public String getFragment() {

		return fragment;
	}

	@Override
	public String toString() {

		return new StringBuilder()//
			.append(scheme)
			.append("://")
			.append(domainName)
			.append(port != null? ":" + port : "")
			.append(getStartingFromPath())
			.toString();
	}

	public String getStartingFromPath() {

		StringBuilder builder = new StringBuilder();

		if (path != null) {
			if (!path.startsWith("/")) {
				builder.append("/");
			}
			builder.append(path);
		}

		if (!parameters.isEmpty()) {
			builder.append("?").append(getQueryString());
		}

		if (fragment != null) {
			builder.append("#").append(fragment);
		}

		return builder.toString();
	}

	private static String convertToQueryString(Entry<String, List<String>> parameter) {

		var encodedKey = UrlCoding.encodeUtf8(parameter.getKey());
		var values = parameter.getValue();
		if (isEmpty(values)) {
			return encodedKey;
		} else {
			return values//
				.stream()
				.map(UrlCoding::encodeUtf8)
				.map(encodedValue -> encodedKey + "=" + encodedValue)
				.collect(Collectors.joining("&"));
		}
	}

	private static boolean isEmpty(List<String> values) {

		return values.isEmpty() || values.size() == 1 && values.get(0).isEmpty();
	}
}
