package com.softicar.platform.common.servlet.hot;

import com.softicar.platform.common.servlet.server.AbstractHttpServletServer;
import com.softicar.platform.common.servlet.service.IHttpService;
import java.util.Objects;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * A server with hot-deployment for an {@link IHttpService}.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class HotHttpServer extends AbstractHttpServletServer<HotHttpServer> {

	private final IHotHttpServiceLoader serviceLoader;

	public HotHttpServer(Class<? extends IHttpService> serviceClass) {

		this(new HotHttpServiceLoader(serviceClass));
	}

	public HotHttpServer(IHotHttpServiceLoader servletLoader) {

		this.serviceLoader = Objects.requireNonNull(servletLoader);
	}

	@Override
	protected HotHttpServer getThis() {

		return this;
	}

	@Override
	protected ServletHolder createServletHolder() {

		return new ServletHolder(new HotHttpServiceServlet(serviceLoader));
	}
}
