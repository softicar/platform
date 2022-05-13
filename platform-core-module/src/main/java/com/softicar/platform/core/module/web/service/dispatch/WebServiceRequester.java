package com.softicar.platform.core.module.web.service.dispatch;

import com.softicar.platform.ajax.simple.SimpleHttpSession;
import com.softicar.platform.ajax.simple.SimpleServletRequest;
import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.core.module.web.service.WebServiceIdFetcher;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.io.InputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

/**
 * Front-end to create and send a {@link SimpleServletRequest}.
 */
public class WebServiceRequester {

	private final SimpleServletRequest request;

	public WebServiceRequester(Class<? extends IWebService> serviceClass) {

		this(EmfSourceCodeReferencePoints.getUuidOrThrow(serviceClass));
	}

	public WebServiceRequester(UUID serviceUuid) {

		this.request = new SimpleServletRequest();
		this.request.setParameter(WebServiceIdFetcher.getIdParameterName(), serviceUuid.toString());
		this.request.setRequestUrl(getServiceUrl(serviceUuid));
		this.request.setSession(new SimpleHttpSession(""));
	}

	private static String getServiceUrl(UUID serviceUuid) {

		String absoluteUrl = new WebServiceUrlBuilder(serviceUuid).build().toString();
		return absoluteUrl.split("\\?", 2)[0];
	}

	public WebServiceRequester setRequestMethod(WebServiceRequestMethod requestMethod) {

		this.request.setMethod(requestMethod + "");
		return this;
	}

	public WebServiceRequester setContent(InputStream content) {

		request.setContent(StreamUtils.toByteArray(content));
		return this;
	}

	public WebServiceRequester setContent(byte[] content) {

		request.setContent(content);
		return this;
	}

	public WebServiceRequester setHeader(String key, String value) {

		this.request.setHeader(key, value);
		return this;
	}

	public WebServiceRequester setBase64Authorization(String loginName, String password) {

		String credentials = loginName + ':' + password;
		String encodedCredentials = Base64.getEncoder().encodeToString(Utf8Convering.toUtf8(credentials));
		return setHeader("Authorization", "Basic " + encodedCredentials);
	}

	public WebServiceRequester setParameter(String key, String value) {

		this.request.setParameter(key, value);
		return this;
	}

	public SimpleServletResponse request() {

		Objects.requireNonNull(request.getMethod(), "missing request method");
		return CurrentWebServiceDispatcher.getDispatcher().dispatch(request);
	}
}
