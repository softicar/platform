package com.softicar.platform.common.network.url;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.stream.NullOutputStream;
import com.softicar.platform.common.string.formatting.Formatting;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.net.util.Base64;

/**
 * A simple {@link URL} requester fetching data from a given HTTP address.
 * <p>
 * TODO: remove dependency on <i>org.apache</i>
 *
 * @author Oliver Richers
 */
public class UrlRequester {

	private static final int DEFAULT_TIMEOUT = 15 * 1000;
	private URL url;
	private String username;
	private String password;
	private int connectTimeout;
	private int readTimeout;

	public UrlRequester() {

		this.url = null;
		this.username = null;
		this.password = null;
		this.connectTimeout = DEFAULT_TIMEOUT;
		this.readTimeout = DEFAULT_TIMEOUT;
	}

	public UrlRequester setUrl(URL url) {

		this.url = url;
		return this;
	}

	public UrlRequester setUrl(String urlString, Object...args) {

		try {
			this.url = new URL(Formatting.format(urlString, args));
		} catch (MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
		return this;
	}

	public UrlRequester setUsername(String username) {

		this.username = username;
		return this;
	}

	public UrlRequester setPassword(String password) {

		this.password = password;
		return this;
	}

	public UrlRequester setConnectTimeout(int connectTimeout) {

		this.connectTimeout = connectTimeout;
		return this;
	}

	public UrlRequester setReadTimeout(int readTimeout) {

		this.readTimeout = readTimeout;
		return this;
	}

	public InputStream getInputStream() {

		try {
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(connectTimeout);
			connection.setReadTimeout(readTimeout);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			if (username != null && password != null) {
				connection.setRequestProperty("Authorization", "Basic " + getAuthString());
			}
			return connection.getInputStream();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private String getAuthString() {

		String authString = username + ":" + password;
		byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
		return new String(authEncBytes);
	}

	public void ping() {

		try (InputStream inputStream = getInputStream(); NullOutputStream outputStream = new NullOutputStream()) {
			StreamUtils.copy(inputStream, outputStream);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	public byte[] read() {

		try (InputStream inputStream = getInputStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			StreamUtils.copy(inputStream, outputStream);
			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
