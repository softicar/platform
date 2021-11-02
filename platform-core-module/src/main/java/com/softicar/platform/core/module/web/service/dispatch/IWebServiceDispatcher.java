package com.softicar.platform.core.module.web.service.dispatch;

import com.softicar.platform.ajax.simple.SimpleServletRequest;
import com.softicar.platform.ajax.simple.SimpleServletResponse;

public interface IWebServiceDispatcher {

	SimpleServletResponse dispatch(SimpleServletRequest request);
}
