package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.web.service.WebServiceServer;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.page.service.PageServiceFactory;
import com.softicar.platform.db.core.database.IDbDatabase;

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
 *         database.apply(() -> {...});
 *
 *         new WebServiceTestServer(database)//
 *             .setPort(9000)
 *             .startAndJoin();
 *     }
 * }
 * </pre>
 *
 * @author Oliver Richers
 */
public class WebServiceTestServer extends WebServiceServer {

	public WebServiceTestServer(IDbDatabase database) {

		super(new WebServiceTestService(database));

		setContextName(AGCoreModuleInstance.PORTAL_APPLICATION.getDefault());
		setRequestString("service/" + SourceCodeReferencePoints.getUuidOrThrow(PageServiceFactory.class));
	}
}
