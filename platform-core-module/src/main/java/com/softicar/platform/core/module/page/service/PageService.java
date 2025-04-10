package com.softicar.platform.core.module.page.service;

import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.common.web.service.IWebService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PageService implements IWebService {

	private AjaxFramework ajaxFramework;

	public PageService() {

		this.ajaxFramework = new AjaxFramework(new PageServiceStrategy());
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
