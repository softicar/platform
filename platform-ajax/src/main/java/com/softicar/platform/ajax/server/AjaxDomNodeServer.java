package com.softicar.platform.ajax.server;

import com.softicar.platform.common.web.service.hot.HotWebServiceServer;
import com.softicar.platform.common.web.servlet.HttpServletServer;
import com.softicar.platform.dom.document.DomDocument;
import com.softicar.platform.dom.node.DomNode;
import com.softicar.platform.dom.node.IDomNode;

/**
 * Special {@link HttpServletServer} server with hot code deployment that serves
 * a {@link DomDocument} containing a single {@link DomNode}.
 *
 * @author Oliver Richers
 */
public class AjaxDomNodeServer extends HotWebServiceServer {

	public AjaxDomNodeServer(Class<? extends IDomNode> nodeClass) {

		super(new AjaxDomNodeServiceLoader(nodeClass));
	}

	public static void main(String[] args) {

		new AjaxDomNodeServer(AjaxDomNodeServerTestDiv.class)//
			.setPort(9000)
			.startAndJoin();
	}
}
