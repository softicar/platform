package com.softicar.platform.core.module.web.service.dispatch.http;

import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HttpWebServiceResponse extends SimpleServletResponse {

	public HttpWebServiceResponse(HttpURLConnection connection) {

		try {
			setStatus(connection.getResponseCode());
			setContentType(connection.getContentType());
			setCharacterEncoding(connection.getContentEncoding());
			copyContent(connection);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void copyContent(HttpURLConnection connection) throws IOException {

		byte[] content = readContent(connection);
		try (OutputStream outputStream = getOutputStream()) {
			StreamUtils.copy(new ByteArrayInputStream(content), outputStream);
		}
	}

	private byte[] readContent(HttpURLConnection connection) throws IOException {

		try (InputStream inputStream = connection.getInputStream()) {
			return StreamUtils.toByteArray(inputStream);
		} catch (Exception exception) {
			exception.printStackTrace();
			try (InputStream errorStream = connection.getErrorStream()) {
				return StreamUtils.toByteArray(errorStream);
			}
		}
	}
}
