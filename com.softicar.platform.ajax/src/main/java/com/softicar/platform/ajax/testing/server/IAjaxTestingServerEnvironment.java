package com.softicar.platform.ajax.testing.server;

/**
 * Provides initialization and cleanup operations for {@link AjaxTestingServer}.
 *
 * @author Alexander Schmidt
 */
public interface IAjaxTestingServerEnvironment {

	/**
	 * Initializes the environment, before the server is started.
	 */
	void setup();

	/**
	 * Cleans the environment, after the server was terminated.
	 */
	void teardown();
}
