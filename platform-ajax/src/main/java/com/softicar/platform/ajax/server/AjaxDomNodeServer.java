package com.softicar.platform.ajax.server;

import com.softicar.platform.common.servlet.hot.HotHttpServiceServlet;
import com.softicar.platform.common.servlet.hot.IHotHttpServiceLoader;
import com.softicar.platform.common.servlet.server.AbstractHttpServletServer;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.node.DomNode;
import com.softicar.platform.dom.node.IDomNode;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Special Jetty server with hot code deployment that serves a
 * {@link DomDocument} containing a single {@link DomNode}.
 *
 * @author Oliver Richers
 */
public class AjaxDomNodeServer extends AbstractHttpServletServer<AjaxDomNodeServer> {

	private final IHotHttpServiceLoader serviceLoader;

	public AjaxDomNodeServer(Class<? extends IDomNode> nodeClass) {

		this.serviceLoader = new AjaxDomNodeServiceLoader(nodeClass);
	}

	@Override
	protected ServletHolder createServletHolder() {

		return new ServletHolder(new HotHttpServiceServlet(serviceLoader));
	}

	@Override
	protected AjaxDomNodeServer getThis() {

		return this;
	}
}
