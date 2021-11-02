package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.core.module.test.SofticarTestDatabase;
import com.softicar.platform.core.module.web.service.WebServiceServlet;

/**
 * Base class for {@link WebServiceServlet} implementations for development with
 * hot deployment, that is, with automatic class reloading.
 * <p>
 * See {@link HotDeploymentWebServiceServer} for an example.
 *
 * @author Oliver Richers
 * @see HotDeploymentWebServiceServer
 */
public class HotDeploymentWebServiceServlet extends WebServiceServlet {

	protected final SofticarTestDatabase database;

	public HotDeploymentWebServiceServlet() {

		this.database = new SofticarTestDatabase();

		setEnvironment(new WebServiceTestEnvironment(database));
	}
}
