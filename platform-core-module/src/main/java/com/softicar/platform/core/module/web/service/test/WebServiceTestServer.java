package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.common.web.service.WebServiceServer;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.test.SofticarTestDatabase;

/**
 * A {@link WebServiceServer} for {@link WebServiceTestService}.
 * <p>
 * This server does <b>not</b> employ hot code deployment. For that, consider
 * the class {@link HotWebServiceTestServer}.
 *
 * <pre>
 * public class Test {
 *
 *     public static void main(String[] args) {
 *
 *         SofticarTestDatabase database = new SofticarTestDatabase();
 *
 *         database.applyFixture(() -> {...});
 *
 *         new WebServiceTestServer(database)//
 *             .setRequestString(...)
 *             .setPort(9000)
 *             .start();
 *     }
 * }
 * </pre>
 *
 * @author Oliver Richers
 */
public class WebServiceTestServer extends WebServiceServer {

	public WebServiceTestServer(SofticarTestDatabase database) {

		super(new WebServiceTestService(database));

		setContextName(AGCoreModuleInstance.PORTAL_APPLICATION.getDefault());
	}
}
