package com.softicar.platform.core.module.user.session;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.framework.listener.AjaxSessionListener;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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

		if (!ajaxSession.getUser().hasModulePermission(CorePermissions.SYSTEM_ADMINISTRATION)) {
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
