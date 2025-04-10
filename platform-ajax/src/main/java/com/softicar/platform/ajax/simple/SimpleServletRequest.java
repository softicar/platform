package com.softicar.platform.ajax.simple;

import com.softicar.platform.common.container.iterator.IteratorEnumeration;
import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.common.io.network.UrlCoding;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class SimpleServletRequest implements HttpServletRequest {

	private String method;
	private String requestUrl;
	private final Map<String, String[]> parameterMap;
	private final ListTreeMap<String, String> headers;
	private final Map<String, Object> attributes;
	private byte[] content;
	private String characterEncoding;
	private HttpSession session;

	public SimpleServletRequest() {

		this.method = null;
		this.requestUrl = "";
		this.parameterMap = new TreeMap<>();
		this.headers = new ListTreeMap<>();
		this.attributes = new TreeMap<>();
		this.content = new byte[0];
		this.characterEncoding = null;
	}

	// -------------------- request method -------------------- //

	public void setMethod(String method) {

		this.method = method;
	}

	@Override
	public String getMethod() {

		return method;
	}

	// -------------------- request URL and query string -------------------- //

	@Override
	public String getQueryString() {

		return getParameterMap()//
			.entrySet()
			.stream()
			.map(SimpleServletRequest::convertToQueryString)
			.collect(Collectors.joining("&"));
	}

	private static String convertToQueryString(Entry<String, String[]> parameter) {

		String[] values = parameter.getValue();
		if (values.length == 0) {
			return UrlCoding.encodeUtf8(parameter.getKey());
		} else {
			return Arrays//
				.asList(values)
				.stream()
				.map(value -> UrlCoding.encodeUtf8(parameter.getKey()) + "=" + UrlCoding.encodeUtf8(value))
				.collect(Collectors.joining("&"));
		}
	}

	/**
	 * Defines the URL to be returned by {@link #getRequestURL()}.
	 * <p>
	 * This also defined the return value of {@link #getRequestURI()}, by simply
	 * dropping the schema and domain name from the supplied URL.
	 */
	public void setRequestUrl(String requestUrl) {

		this.requestUrl = requestUrl;
	}

	@Override
	public String getRequestURI() {

		if (requestUrl.matches(".*://.*/.*")) {
			// remove schema and domain name
			return requestUrl.replaceFirst(".*://.*/", "/");
		} else {
			return requestUrl;
		}
	}

	@Override
	public StringBuffer getRequestURL() {

		return new StringBuffer(requestUrl);
	}

	// -------------------- parameters -------------------- //

	public void setParameter(String name, String value) {

		this.parameterMap.put(name, new String[] { value });
	}

	@Override
	public String getParameter(String parameterName) {

		String[] parameterValues = parameterMap.get(parameterName);
		return parameterValues != null? parameterValues[0] : null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		return parameterMap;
	}

	@Override
	public Enumeration<String> getParameterNames() {

		return new IteratorEnumeration<>(parameterMap.keySet().iterator());
	}

	@Override
	public String[] getParameterValues(String parameterName) {

		return parameterMap.get(parameterName);
	}

	// -------------------- header -------------------- //

	public void setHeader(String headerName, String value) {

		headers.addToList(headerName.toLowerCase(), value);
	}

	@Override
	public String getHeader(String headerName) {

		List<String> list = headers.getList(headerName.toLowerCase());
		return list.isEmpty()? null : list.get(0);
	}

	@Override
	public Enumeration<String> getHeaderNames() {

		return new IteratorEnumeration<>(headers.keySet().iterator());
	}

	@Override
	public Enumeration<String> getHeaders(String headerName) {

		List<String> list = headers.getList(headerName.toLowerCase());
		return new IteratorEnumeration<>(list.iterator());
	}

	@Override
	public int getIntHeader(String headerName) {

		String header = getHeader(headerName);
		return header != null? Integer.parseInt(header) : -1;
	}

	@Override
	public long getDateHeader(String headerName) {

		throw new UnsupportedOperationException("date headers not supported");
	}

	// -------------------- content -------------------- //

	public void setContent(byte[] content) {

		this.content = content;
	}

	@Override
	public int getContentLength() {

		String contentLength = getHeader(HttpHeaderNames.CONTENT_LENGTH);
		return contentLength != null? Integer.parseInt(contentLength) : content.length;
	}

	@Override
	public long getContentLengthLong() {

		return getContentLength();
	}

	public void setContentType(String contentType) {

		setHeader(HttpHeaderNames.CONTENT_TYPE, contentType);
	}

	@Override
	public String getContentType() {

		return getHeader(HttpHeaderNames.CONTENT_TYPE);
	}

	@Override
	public ServletInputStream getInputStream() {

		return new SimpleServletInputStream(content);
	}

	@Override
	public BufferedReader getReader() {

		return new BufferedReader(createContentReader());
	}

	private InputStreamReader createContentReader() {

		if (characterEncoding != null) {
			try {
				return new InputStreamReader(getInputStream(), characterEncoding);
			} catch (UnsupportedEncodingException exception) {
				throw new RuntimeException(exception);
			}
		} else {
			return new InputStreamReader(getInputStream());
		}
	}

	@Override
	public String getCharacterEncoding() {

		return characterEncoding;
	}

	@Override
	public void setCharacterEncoding(String characterEncoding) {

		this.characterEncoding = characterEncoding;
	}

	// -------------------- session -------------------- //

	public SimpleServletRequest setSession(HttpSession session) {

		this.session = session;
		return this;
	}

	@Override
	public HttpSession getSession() {

		return session;
	}

	@Override
	public HttpSession getSession(boolean createSessionAutomatically) {

		if (session == null && createSessionAutomatically) {
			this.session = new SimpleHttpSession("");
		}
		return session;
	}

	// -------------------- session ID -------------------- //

	@Override
	public String getRequestedSessionId() {

		return null;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {

		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {

		return false;
	}

	@Override
	@Deprecated
	public boolean isRequestedSessionIdValid() {

		return false;
	}

	@Override
	public String changeSessionId() {

		throw new UnsupportedOperationException();
	}

	// -------------------- attributes -------------------- //

	@Override
	public Object getAttribute(String name) {

		return attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {

		attributes.put(name, value);
	}

	@Override
	public Enumeration<String> getAttributeNames() {

		return new IteratorEnumeration<>(attributes.keySet().iterator());
	}

	@Override
	public void removeAttribute(String name) {

		attributes.remove(name);
	}

	// -------------------- protocol and address -------------------- //

	@Override
	public String getProtocol() {

		return null;
	}

	@Override
	public String getRemoteAddr() {

		return null;
	}

	@Override
	public String getRemoteHost() {

		return null;
	}

	@Override
	public int getRemotePort() {

		return 0;
	}

	@Override
	public String getLocalName() {

		return null;
	}

	@Override
	public String getLocalAddr() {

		return null;
	}

	@Override
	public int getLocalPort() {

		return 0;
	}

	@Override
	public String getScheme() {

		return null;
	}

	@Override
	public String getServerName() {

		return null;
	}

	@Override
	public int getServerPort() {

		return 0;
	}

	@Override
	public boolean isSecure() {

		return false;
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) {

		throw new UnsupportedOperationException();
	}

	// -------------------- cookies -------------------- //

	@Override
	public Cookie[] getCookies() {

		return null;
	}

	// -------------------- parts -------------------- //

	@Override
	public Collection<Part> getParts() {

		return null;
	}

	@Override
	public Part getPart(String name) {

		return null;
	}

	// -------------------- authentication and login -------------------- //

	@Override
	public String getAuthType() {

		return null;
	}

	@Override
	public Principal getUserPrincipal() {

		return null;
	}

	@Override
	public String getRemoteUser() {

		return null;
	}

	@Override
	public boolean isUserInRole(String arg0) {

		return false;
	}

	@Override
	public boolean authenticate(HttpServletResponse response) {

		return false;
	}

	@Override
	public void login(String username, String password) {

		// nothing to do by default
	}

	@Override
	public void logout() {

		// nothing to do by default
	}

	// -------------------- locale -------------------- //

	@Override
	public Locale getLocale() {

		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {

		return null;
	}

	// -------------------- context and paths -------------------- //

	@Override
	public String getContextPath() {

		return null;
	}

	@Override
	public String getPathInfo() {

		return null;
	}

	@Override
	public String getPathTranslated() {

		return null;
	}

	@Override
	public String getServletPath() {

		return null;
	}

	@Override
	public ServletContext getServletContext() {

		return null;
	}

	// -------------------- asynchronous context -------------------- //

	@Override
	public AsyncContext startAsync() {

		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {

		return null;
	}

	@Override
	public boolean isAsyncStarted() {

		return false;
	}

	@Override
	public boolean isAsyncSupported() {

		return false;
	}

	@Override
	public AsyncContext getAsyncContext() {

		return null;
	}

	// -------------------- dispatcher -------------------- //

	@Override
	public DispatcherType getDispatcherType() {

		return null;
	}

	@Override
	public String getRequestId() {
		return null;
	}

	@Override
	public String getProtocolRequestId() {
		return null;
	}

	@Override
	public ServletConnection getServletConnection() {
		return null;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {

		return null;
	}
}
