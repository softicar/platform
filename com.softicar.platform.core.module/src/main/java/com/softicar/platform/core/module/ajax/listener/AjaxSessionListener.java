package com.softicar.platform.core.module.ajax.listener;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.core.module.log.LogDb;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * This class listens to session events and puts the session on creation and/or
 * activation into a map. This map is saved as attribute of the ServletContext.
 * <p>
 * WARNING: DO NOT RENAME OR MOVE THIS FILE.
 *
 * @author Oliver Richers
 */
public class AjaxSessionListener implements HttpSessionListener, HttpSessionActivationListener, Serializable {

	private static final String ATTRIBUTE_NAME = AjaxSessionListener.class.getCanonicalName();
	private static final int PANIC_SESSION_COUNT = 1000;
	private boolean sessionCountPanicSent = false;

	// ******************************************************************************** //
	// * event functions * //
	// ******************************************************************************** //

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {

		HttpSession session = sessionEvent.getSession();
		Log.finfo("SESSION CREATED: %s", session.getId());
		putSessionIntoMap(session);
		addThisToSession(session);

		checkForPanicSessionCount(session);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {

		HttpSession session = sessionEvent.getSession();
		Log.finfo("SESSION DESTROYED: %s", session.getId());
		removeSessionFromMap(session);
	}

	@Override
	public void sessionDidActivate(HttpSessionEvent sessionEvent) {

		HttpSession session = sessionEvent.getSession();
		putSessionIntoMap(session);
		addThisToSession(session);

		Log.finfo("SESSION DID ACTIVATE: %s", session.getId());
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent sessionEvent) {

		HttpSession session = sessionEvent.getSession();
		removeSessionFromMap(session);

		Log.finfo("SESSION WILL PASSIVATE: %s", session.getId());
	}

	// ******************************************************************************** //
	// * get sessions function * //
	// ******************************************************************************** //

	/**
	 * Returns a new list of sessions objects.
	 */
	public static Iterable<HttpSession> getSessions(ServletContext context) {

		synchronized (context) {
			ArrayList<HttpSession> sessions = new ArrayList<>();
			sessions.addAll(getSessionMap(context).values());
			return sessions;
		}
	}

	// ******************************************************************************** //
	// * private functions * //
	// ******************************************************************************** //

	private void checkForPanicSessionCount(HttpSession session) {

		ServletContext context = session.getServletContext();
		synchronized (context) {
			if (!sessionCountPanicSent) {
				int sessionCount = getSessionMap(context).size();
				if (sessionCount >= PANIC_SESSION_COUNT) {
					LogDb.setProcessClass(getClass());
					LogDb.panic("There are %d sessions on the tomcat now.", sessionCount);
					sessionCountPanicSent = true;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, HttpSession> getSessionMap(ServletContext context) {

		Map<String, HttpSession> sessionMap = (Map<String, HttpSession>) context.getAttribute(ATTRIBUTE_NAME);
		if (sessionMap == null) {
			context.setAttribute(ATTRIBUTE_NAME, sessionMap = new TreeMap<>());
		}
		return sessionMap;
	}

	private static void putSessionIntoMap(HttpSession session) {

		ServletContext context = session.getServletContext();
		synchronized (context) {
			getSessionMap(context).put(session.getId(), session);
		}
	}

	private static void removeSessionFromMap(HttpSession session) {

		ServletContext context = session.getServletContext();
		synchronized (context) {
			getSessionMap(context).remove(session.getId());
		}
	}

	/**
	 * This function adds this listener as attribute to the session.
	 * <p>
	 * If you don't do this, the listener will not receive session passivation
	 * or activation events.
	 */
	private void addThisToSession(HttpSession session) {

		session.setAttribute(ATTRIBUTE_NAME, this);
	}
}
