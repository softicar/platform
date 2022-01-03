package com.softicar.platform.ajax.server.development;

import com.softicar.platform.ajax.server.hot.HotDeploymentServlet;
import com.softicar.platform.ajax.server.hot.IHotDeploymentServletLoader;
import com.softicar.platform.ajax.server.standalone.AbstractStandAloneServletServer;
import com.softicar.platform.dom.node.IDomNode;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Special Jetty server with hot deployment, aimed at software development.
 *
 * @author Oliver Richers
 */
public class AjaxDevelopmentServer extends AbstractStandAloneServletServer<AjaxDevelopmentServer> {

	private final IHotDeploymentServletLoader servletLoader;

	public AjaxDevelopmentServer(Class<? extends IDomNode> nodeClass) {

		this.servletLoader = new AjaxDevelopmentServerServletLoader(nodeClass);
	}

	@Override
	protected ServletHolder createServletHolder() {

		return new ServletHolder(new HotDeploymentServlet(servletLoader));
	}

	@Override
	protected AjaxDevelopmentServer getThis() {

		return this;
	}
}
