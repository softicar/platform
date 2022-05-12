package com.softicar.platform.ajax.server;

import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.common.servlet.service.IHttpService;
import com.softicar.platform.dom.node.IDomNode;
import java.util.EventListener;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class AjaxDomNodeService implements IHttpService {

	private final AjaxFramework ajaxFramework;

	public AjaxDomNodeService(Class<? extends IDomNode> nodeClass) {

		this(() -> ReflectionUtils.newInstance(nodeClass));
	}

	public AjaxDomNodeService(Supplier<IDomNode> nodeFactory) {

		this.ajaxFramework = new AjaxFramework(new AjaxDomNodeServiceAjaxStrategy(nodeFactory));
	}

	@Override
	public void initialize(Consumer<EventListener> listeners) {

		ajaxFramework.initialize(listeners);
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		ajaxFramework.service(request, response);
	}
}
