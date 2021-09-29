package com.softicar.platform.ajax.server.hot;

import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Simple implementation of {@link ServletConfig} for
 * {@link HotDeploymentServletServer}.
 *
 * @author Oliver Richers
 */
class HotDeploymentServletConfig implements ServletConfig {

	private final ServletContext servletContext;

	public HotDeploymentServletConfig(ServletContext servletContext) {

		this.servletContext = servletContext;
	}

	@Override
	public String getServletName() {

		return "";
	}

	@Override
	public ServletContext getServletContext() {

		return servletContext;
	}

	@Override
	public String getInitParameter(String name) {

		return null;
	}

	@Override
	public Enumeration<String> getInitParameterNames() {

		return Collections.emptyEnumeration();
	}
}
