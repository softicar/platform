package com.softicar.platform.core.module.web.service.dispatch;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.core.module.web.service.dispatch.http.HttpWebServiceDispatcher;

public class CurrentWebServiceDispatcher {

	private static final Singleton<IWebServiceDispatcher> INSTANCE = new Singleton<>(HttpWebServiceDispatcher::new);

	public static IWebServiceDispatcher getDispatcher() {

		return INSTANCE.get();
	}

	public static void setDispatcher(IWebServiceDispatcher dispatcher) {

		INSTANCE.set(dispatcher);
	}
}
