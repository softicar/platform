package com.softicar.platform.ajax.server.hot;

import com.softicar.platform.ajax.server.standalone.AbstractStandAloneServletServer;
import java.util.Objects;
import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * A stand-alone, hot-deployment capable server for a single
 * {@link HttpServlet}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class HotDeploymentServletServer extends AbstractStandAloneServletServer<HotDeploymentServletServer> {

	private final IHotDeploymentServletLoader servletLoader;

	public HotDeploymentServletServer(Class<? extends HttpServlet> servletClass) {

		this(new HotDeploymentServletLoader(servletClass));
	}

	public HotDeploymentServletServer(IHotDeploymentServletLoader servletLoader) {

		this.servletLoader = Objects.requireNonNull(servletLoader);
	}

	@Override
	protected HotDeploymentServletServer getThis() {

		return this;
	}

	@Override
	protected ServletHolder createServletHolder() {

		return new ServletHolder(new HotDeploymentServlet(servletLoader));
	}
}
