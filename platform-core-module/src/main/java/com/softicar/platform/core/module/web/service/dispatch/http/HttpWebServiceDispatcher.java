package com.softicar.platform.core.module.web.service.dispatch.http;

import com.softicar.platform.ajax.simple.SimpleServletRequest;
import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.core.module.web.service.dispatch.IWebServiceDispatcher;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Enumeration;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

public class HttpWebServiceDispatcher implements IWebServiceDispatcher {

	@Override
	public SimpleServletResponse dispatch(SimpleServletRequest request) {

		try {
			// create service URL
			URL url = createUrl(request);

			// open connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(request.getMethod());

			// add headers
			Enumeration<String> keyEnumeration = request.getHeaderNames();
			while (keyEnumeration.hasMoreElements()) {
				String headerName = keyEnumeration.nextElement();
				String headerContent = request.getHeader(headerName);
				connection.setRequestProperty(headerName, headerContent);
			}

			if (connection instanceof HttpsURLConnection) {
				try {
					SSLContext sc = SSLContext.getInstance("TLS");
					sc.init(null, getTrustManagers(), new SecureRandom());
					HttpsURLConnection httpsUrlConnection = (HttpsURLConnection) connection;
					httpsUrlConnection.setSSLSocketFactory(sc.getSocketFactory());
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}

			// send data
			if (request.getContentLength() > 0) {
				connection.setDoOutput(true);
				try (//
						var outputStream = connection.getOutputStream();
						var inputStream = request.getInputStream()) {
					StreamUtils.copy(inputStream, outputStream);
				}
			}

			connection.connect();

			return new HttpWebServiceResponse(connection);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private static TrustManager[] getTrustManagers() {

		return new TrustManager[] { new ExtendedTrustManager() };
	}

	private static URL createUrl(HttpServletRequest request) {

		StringBuilder urlBuilder = new StringBuilder().append(request.getRequestURL().toString());
		String queryString = request.getQueryString();
		if (queryString != null) {
			urlBuilder.append('?').append(queryString);
		}
		return createUrl(urlBuilder.toString());
	}

	private static URL createUrl(String url) {

		try {
			return URL.of(URI.create(url), null);
		} catch (MalformedURLException exception) {
			throw new RuntimeException(exception);
		}
	}
}
