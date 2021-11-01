package com.softicar.platform.core.module.web.service.dispatch.emulation;

import java.util.Enumeration;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import org.mockito.Mockito;

/**
 * A dummy implementation of {@link ServletConfig}.
 *
 * @author Oliver Richers
 */
class EmulatedServletConfig implements ServletConfig {

	private final ServletContext context;

	public EmulatedServletConfig() {

		this.context = Mockito.mock(ServletContext.class);
	}

	@Override
	public String getServletName() {

		throw new UnsupportedOperationException();
	}

	@Override
	public ServletContext getServletContext() {

		return context;
	}

	@Override
	public String getInitParameter(String name) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Enumeration<String> getInitParameterNames() {

		throw new UnsupportedOperationException();
	}
}
