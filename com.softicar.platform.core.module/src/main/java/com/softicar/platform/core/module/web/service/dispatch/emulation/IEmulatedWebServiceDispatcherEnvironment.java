package com.softicar.platform.core.module.web.service.dispatch.emulation;

import com.softicar.platform.core.module.web.service.environment.IWebServiceEnvironment;

public interface IEmulatedWebServiceDispatcherEnvironment {

	default void setupDispatcherEnvironment() {

		// nothing
	}

	default void cleanupDispatcherEnvironment() {

		// nothing
	}

	IWebServiceEnvironment createRequestEnvironment();
}
