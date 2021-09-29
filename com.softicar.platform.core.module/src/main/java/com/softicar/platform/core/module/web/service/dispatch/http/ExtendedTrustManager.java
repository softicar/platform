package com.softicar.platform.core.module.web.service.dispatch.http;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class ExtendedTrustManager extends CompositeTrustManager {

	public ExtendedTrustManager(KeyStore customKeyStore) {

		this();
		addTrustManager(createTrustManager(customKeyStore));
	}

	public ExtendedTrustManager() {

		addTrustManager(createDefaultTrustManager());
	}

	private X509TrustManager createDefaultTrustManager() {

		// Note that {@link TrustManagerFactory#init(KeyStore)} called with a null
		// argument results in the default trust manager being returned.
		return createTrustManager(null);
	}

	private X509TrustManager createTrustManager(KeyStore store) {

		try {
			String algorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory factory = TrustManagerFactory.getInstance(algorithm);
			factory.init(store);
			return (X509TrustManager) factory.getTrustManagers()[0];
		} catch (KeyStoreException | NoSuchAlgorithmException exception) {
			throw new RuntimeException(exception);
		}
	}
}
