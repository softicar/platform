package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.ajax.server.standalone.AbstractStandAloneServletServer;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.web.service.WebServiceServlet;
import com.softicar.platform.core.module.web.service.environment.IWebServiceEnvironment;
import com.softicar.platform.db.core.database.IDbDatabase;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * A stand-alone server for the execution of unit tests using
 * {@link WebServiceServlet}.
 * <p>
 * This server does <b>not</b> employ hot-deployment class-reloading.
 *
 * <pre>
 * public class Test {
 *
 *     public static void main(String[] args) {
 *
 *         DatabaseTestSetup setup = new DatabaseTestSetup();
 *
 *         setup.applyFixture(...);
 *
 *         new WebServiceTestServer(setup.getDatabase())//
 *             .setRequestString(...)
 *             .setPort(9000)
 *             .start();
 *     }
 * }
 * </pre>
 *
 * @author Oliver Richers
 */
public class WebServiceTestServer extends AbstractStandAloneServletServer<WebServiceTestServer> {

	private final IWebServiceEnvironment environment;

	public WebServiceTestServer(IDbDatabase database) {

		this(new WebServiceTestEnvironment(database));
	}

	public WebServiceTestServer(IWebServiceEnvironment environment) {

		this.environment = environment;

		setContextName(AGCoreModuleInstance.PORTAL_APPLICATION.getDefault());
	}

	@Override
	protected ServletHolder getServletHolder() {

		return new ServletHolder(new WebServiceServlet().setEnvironment(environment));
	}

	@Override
	protected WebServiceTestServer getThis() {

		return this;
	}
}
