package com.softicar.platform.core.module.web.service.dispatch.http;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class TrustAllTrustManager implements X509TrustManager {

	private static final X509Certificate[] X509_CERTIFICATES = new X509Certificate[0];

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) {

		// accept all
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) {

		// accept all
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {

		return X509_CERTIFICATES;
	}
}
