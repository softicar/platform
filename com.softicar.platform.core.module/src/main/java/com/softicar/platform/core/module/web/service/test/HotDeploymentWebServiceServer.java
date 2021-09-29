package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.ajax.server.hot.HotDeploymentServletServer;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;

/**
 * A server for {@link HotDeploymentWebServiceServlet}.
 * <p>
 * Hot deployment prohibits the use of existing servlet instances being supplied
 * to the server. Only the specification of the servlet class, which must have a
 * default constructor, is possible.
 *
 * <pre>
 * public class TestServlet extends HotDeploymentWebServiceServlet {
 *
 *     private static final long serialVersionUID = 1L;
 *
 *     public TestServlet() {
 *
 *         testSetup.applyFixture(...);
 *     }
 *
 *     public static void main(String[] args) {
 *
 *          new HotDeploymentWebServiceServer(TestServlet.class)//
 *              .setRequestString(...)
 *              .setPort(9000)
 *              .start();
 *     }
 * }
 * </pre>
 *
 * @author Oliver Richers
 */
public class HotDeploymentWebServiceServer extends HotDeploymentServletServer {

	public HotDeploymentWebServiceServer(Class<? extends HotDeploymentWebServiceServlet> servletClass) {

		super(servletClass);

		setContextName(AGCoreModuleInstance.PORTAL_APPLICATION.getDefault());
	}
}
