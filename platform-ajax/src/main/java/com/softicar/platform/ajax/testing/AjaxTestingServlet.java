package com.softicar.platform.ajax.testing;

import com.softicar.platform.ajax.framework.AjaxFramework;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AjaxTestingServlet extends HttpServlet {

	private final AjaxTestingStrategy strategy;
	private final AjaxFramework ajaxFramework;

	public AjaxTestingServlet() {

		this.strategy = new AjaxTestingStrategy();
		this.ajaxFramework = new AjaxFramework(strategy);
	}

	public AjaxFramework getAjaxFramework() {

		return ajaxFramework;
	}

	public AjaxTestingStrategy getStrategy() {

		return strategy;
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {

		ajaxFramework.service(request, response);
	}
}
