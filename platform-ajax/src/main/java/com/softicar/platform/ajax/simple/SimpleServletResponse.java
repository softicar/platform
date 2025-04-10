package com.softicar.platform.ajax.simple;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class SimpleServletResponse implements HttpServletResponse {

	private final SimpleServletOutputStream outputStream = new SimpleServletOutputStream();
	private final ListTreeMap<String, String> headers;
	private int status;
	private String statusMessage;
	private String characterEncoding;
	private boolean committed;

	public SimpleServletResponse() {

		this.headers = new ListTreeMap<>();
		this.status = HttpServletResponse.SC_OK;
		this.statusMessage = "";
		this.committed = false;
	}

	// -------------------- status -------------------- //

	public String getStatusMessage() {

		return statusMessage;
	}

	@Override
	public int getStatus() {

		return status;
	}

	@Override
	public void setStatus(int status) {

		this.status = status;
	}

	@Override
	public void sendError(int status) {

		resetBuffer();
		this.status = status;
	}

	@Override
	public void sendError(int status, String message) {

		resetBuffer();
		this.status = status;
		this.statusMessage = message;
	}

	// -------------------- content -------------------- //

	public byte[] getContent() {

		return outputStream.getData();
	}

	@Override
	public ServletOutputStream getOutputStream() {

		return outputStream;
	}

	@Override
	public PrintWriter getWriter() {

		return new PrintWriter(createContentWriter());
	}

	private OutputStreamWriter createContentWriter() {

		if (characterEncoding != null) {
			try {
				return new OutputStreamWriter(getOutputStream(), characterEncoding);
			} catch (UnsupportedEncodingException exception) {
				throw new RuntimeException(exception);
			}
		} else {
			return new OutputStreamWriter(getOutputStream());
		}
	}

	@Override
	public void setContentLength(int contentLength) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setContentLengthLong(long contentLength) {

		throw new UnsupportedOperationException();
	}

	// -------------------- content buffer -------------------- //

	@Override
	public int getBufferSize() {

		return 0;
	}

	@Override
	public void flushBuffer() {

		this.committed = true;
	}

	@Override
	public void resetBuffer() {

		outputStream.reset();
	}

	@Override
	public void setBufferSize(int bufferSize) {

		throw new UnsupportedOperationException();
	}

	// -------------------- content type -------------------- //

	@Override
	public String getContentType() {

		return getHeader(HttpHeaderNames.CONTENT_TYPE);
	}

	@Override
	public void setContentType(String contentType) {

		setHeader(HttpHeaderNames.CONTENT_TYPE, contentType);
	}

	// -------------------- character encoding -------------------- //

	@Override
	public String getCharacterEncoding() {

		return characterEncoding;
	}

	@Override
	public void setCharacterEncoding(String characterEncoding) {

		this.characterEncoding = characterEncoding;
	}

	// -------------------- header -------------------- //

	@Override
	public void addHeader(String headerName, String value) {

		headers.addToList(headerName, value);
	}

	@Override
	public boolean containsHeader(String headerName) {

		return headers.containsKey(headerName);
	}

	@Override
	public String getHeader(String headerName) {

		List<String> list = headers.getList(headerName);
		return list.isEmpty()? null : list.get(0);
	}

	@Override
	public Collection<String> getHeaderNames() {

		return headers.keySet();
	}

	@Override
	public Collection<String> getHeaders(String headerName) {

		return headers.getList(headerName);
	}

	@Override
	public void setHeader(String headerName, String value) {

		List<String> list = headers.getList(headerName);
		list.clear();
		list.add(value);
	}

	// -------------------- date header -------------------- //

	@Override
	public void addDateHeader(String headerName, long value) {

		addHeader(headerName, "" + value);
	}

	@Override
	public void setDateHeader(String headerName, long value) {

		setHeader(headerName, "" + value);
	}

	// -------------------- int header -------------------- //
	@Override
	public void addIntHeader(String headerName, int value) {

		addHeader(headerName, "" + value);
	}

	@Override
	public void setIntHeader(String headerName, int value) {

		setHeader(headerName, "" + value);
	}

	// -------------------- encoding -------------------- //

	@Override
	public String encodeRedirectURL(String arg0) {

		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeURL(String arg0) {

		throw new UnsupportedOperationException();
	}

	// -------------------- locale -------------------- //

	@Override
	public Locale getLocale() {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setLocale(Locale arg0) {

		throw new UnsupportedOperationException();
	}

	// -------------------- miscellaneous -------------------- //

	@Override
	public void reset() {

		resetBuffer();
		this.status = HttpServletResponse.SC_OK;
		this.statusMessage = "";
		this.headers.clear();
	}

	@Override
	public boolean isCommitted() {

		return committed;
	}

	@Override
	public void addCookie(Cookie arg0) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void sendRedirect(String arg0) {

		throw new UnsupportedOperationException();
	}
}
