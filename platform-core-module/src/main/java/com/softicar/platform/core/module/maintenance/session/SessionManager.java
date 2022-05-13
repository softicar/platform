package com.softicar.platform.core.module.maintenance.session;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.framework.listener.AjaxSessionListener;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class SessionManager {

	public static Iterable<HttpSession> getAllSessions() {

		return AjaxSessionListener.getSessions(getServletContext());
	}

	public static void invalidateAllSessions() {

		getAllSessions().forEach(HttpSession::invalidate);
	}

	public static void invalidateAllNonAdministratorSessions() {

		getAllSessions().forEach(SessionManager::invalidateSessionIfNotAdministrator);
	}

	private static void invalidateSessionIfNotAdministrator(HttpSession session) {

		SofticarAjaxSession//
			.getInstance(session)
			.ifPresentOrElse(SessionManager::checkUserAndInvalidateIfNotAdministrator, () -> session.invalidate());
	}

	private static void checkUserAndInvalidateIfNotAdministrator(SofticarAjaxSession ajaxSession) {

		if (!ajaxSession.getUser().hasModuleRole(CoreRoles.SYSTEM_ADMINISTRATOR)) {
			ajaxSession.getSession().invalidate();
		}
	}

	private static ServletContext getServletContext() {

		return AjaxDocument//
			.getCurrentDocument()
			.get()
			.getHttpSession()
			.getServletContext();
	}
}
