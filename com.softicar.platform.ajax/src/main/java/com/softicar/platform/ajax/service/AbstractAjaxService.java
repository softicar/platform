package com.softicar.platform.ajax.service;

import com.softicar.platform.ajax.customization.IAjaxStrategy;
import com.softicar.platform.ajax.framework.IAjaxFramework;
import com.softicar.platform.ajax.request.IAjaxRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAjaxService implements IAjaxService {

	protected final IAjaxFramework framework;
	protected final IAjaxStrategy strategy;
	protected final IAjaxRequest request;
	protected final HttpServletResponse response;

	protected AbstractAjaxService(IAjaxRequest request) {

		this.framework = request.getAjaxFramework();
		this.strategy = framework.getAjaxStrategy();
		this.request = request;
		this.response = request.getServletResponse();
	}
}
