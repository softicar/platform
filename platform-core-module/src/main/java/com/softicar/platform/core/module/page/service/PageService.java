package com.softicar.platform.core.module.page.service;

import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.common.servlet.service.IHttpService;
import java.util.EventListener;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageService implements IHttpService {

	private AjaxFramework ajaxFramework;

	public PageService() {

		this.ajaxFramework = new AjaxFramework(new PageServiceStrategy());
	}

	@Override
	public void initialize(Consumer<EventListener> listeners) {

		ajaxFramework.initialize(listeners);
	}

	@Override
	public void destroy() {

		ajaxFramework = null;
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		ajaxFramework.service(request, response);
	}
}
