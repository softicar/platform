package com.softicar.platform.ajax.testing;

import com.softicar.platform.ajax.framework.AjaxFramework;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public void init() {

		ajaxFramework.initialize(getServletContext());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {

		ajaxFramework.service(request, response);
	}
}
