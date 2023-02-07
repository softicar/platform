package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.common.web.service.hot.HotWebServiceServer;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.page.service.PageServiceFactory;

/**
 * A {@link HotWebServiceServer} for {@link WebServiceTestService}.
 * <p>
 * Hot code deployment prohibits direct references to the {@link IWebService}
 * object. Instead a reference to a class implementing {@link IWebService} has
 * to be provided. This class is then instantiated through reflection.
 *
 * <pre>
 * public class TestService extends WebServiceTestService {
 *
 *     public TestServlet() {
 *
 *         database.applyFixture(() -> {...});
 *     }
 *
 *     public static void main(String[] args) {
 *
 *          new HotWebServiceTestServer(TestService.class)//
 *              .setPort(9000)
 *              .startAndJoin();
 *     }
 * }
 * </pre>
 *
 * @author Oliver Richers
 */
public class HotWebServiceTestServer extends HotWebServiceServer {

	public HotWebServiceTestServer(Class<? extends WebServiceTestService> serviceClass) {

		super(serviceClass);

		setContextName(AGCoreModuleInstance.PORTAL_APPLICATION.getDefault());
		setRequestString("service/" + SourceCodeReferencePoints.getUuidOrThrow(PageServiceFactory.class));
	}
}
