package com.softicar.platform.core.module.web.service.dispatch.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.X509TrustManager;

public class CompositeTrustManager implements X509TrustManager {

	private final List<X509TrustManager> trustManagers;

	public CompositeTrustManager() {

		this.trustManagers = new ArrayList<>();
	}

	public void addTrustManager(X509TrustManager trustManager) {

		this.trustManagers.add(trustManager);
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		checkTrusted(trustManager -> trustManager.checkServerTrusted(chain, authType));
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		checkTrusted(trustManager -> trustManager.checkClientTrusted(chain, authType));
	}

	private static interface PeerTrustChecker {

		void check(X509TrustManager value) throws CertificateException;
	}

	private void checkTrusted(PeerTrustChecker trustChecker) throws CertificateException {

		if (trustManagers.isEmpty()) {
			throw new CertificateException("No trust managers.");
		}

		// try to find a trust manager accepting the certificate of the peer
		Iterator<X509TrustManager> iterator = trustManagers.iterator();
		while (iterator.hasNext()) {
			try {
				trustChecker.check(iterator.next());
				return;
			} catch (CertificateException exception) {
				if (!iterator.hasNext()) {
					// no more trust managers
					throw exception;
				}
			}
		}
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {

		List<X509Certificate> certificates = new ArrayList<>();
		for (X509TrustManager trustManager: trustManagers) {
			certificates.addAll(Arrays.asList(trustManager.getAcceptedIssuers()));
		}
		return certificates.stream().toArray(X509Certificate[]::new);
	}
}
