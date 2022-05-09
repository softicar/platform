package com.softicar.platform.core.module.page.service;

import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.core.module.web.service.IWebService;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointUuid;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EmfSourceCodeReferencePointUuid("95cf1a1b-c12e-4594-9d20-783988fe32b9")
public class PageService implements IWebService {

	private static volatile AjaxFramework ajaxFramework;

	@Override
	public void initialize(ServletContext servletContext) {

		ajaxFramework = new AjaxFramework(new PageServiceStrategy());
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
