package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.common.web.service.IWebService;
import com.softicar.platform.common.web.service.hot.HotWebServiceServer;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;

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
 *          new HotDeploymentWebServiceServer(TestService.class)//
 *              .setRequestString("service?id=" + EmfSourceCodeReferencePoints.getUuidOrThrow(PageServiceFactory.class))
 *              .setPort(9000)
 *              .start();
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
	}
}
