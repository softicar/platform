package com.softicar.platform.ajax.server.development;

import com.softicar.platform.ajax.customization.AbstractAjaxStrategy;
import com.softicar.platform.ajax.customization.IAjaxStrategy;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Supplier;
import javax.servlet.http.HttpSession;

/**
 * Simple implementation of {@link IAjaxStrategy}.
 *
 * @author Oliver Richers
 */
class AjaxDevelopmentServerStrategy extends AbstractAjaxStrategy {

	private final Supplier<IDomNode> nodeFactory;

	public AjaxDevelopmentServerStrategy(Supplier<IDomNode> nodeFactory) {

		this.nodeFactory = nodeFactory;
	}

	@Override
	public void buildDocument(IAjaxDocument document) {

		document.appendToBody(nodeFactory.get());
	}

	@Override
	public boolean isSuperUser(HttpSession session) {

		return true;
	}
}
