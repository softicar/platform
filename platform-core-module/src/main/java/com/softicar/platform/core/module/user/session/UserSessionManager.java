package com.softicar.platform.core.module.user.session;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.framework.listener.AjaxSessionListener;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

public class UserSessionManager {

	private final ServletContext context;

	public UserSessionManager() {

		this.context = getServletContext();
	}

	public Iterable<HttpSession> getAllSessions() {

		return AjaxSessionListener.getSessions(context);
	}

	public void invalidateAllSessions() {

		getAllSessions().forEach(HttpSession::invalidate);
	}

	public void invalidateAllNonAdministratorSessions() {

		getAllSessions().forEach(this::invalidateSessionIfNotAdministrator);
	}

	private void invalidateSessionIfNotAdministrator(HttpSession session) {

		SofticarAjaxSession//
			.getInstance(session)
			.ifPresentOrElse(it -> checkUserAndInvalidateIfNotAdministrator(it, session), () -> session.invalidate());
	}

	private void checkUserAndInvalidateIfNotAdministrator(SofticarAjaxSession ajaxSession, HttpSession session) {

		if (!CorePermissions.ADMINISTRATION.test(AGCoreModuleInstance.getInstance(), ajaxSession.getUser())) {
			session.invalidate();
		}
	}

	private ServletContext getServletContext() {

		return AjaxDocument//
			.getCurrentDocument()
			.get()
			.getHttpSession()
			.getServletContext();
	}
}
