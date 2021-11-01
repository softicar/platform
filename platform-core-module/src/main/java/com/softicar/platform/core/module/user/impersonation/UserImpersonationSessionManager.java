package com.softicar.platform.core.module.user.impersonation;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpSession;

/**
 * This class facilitates session handling, related to user impersonation.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class UserImpersonationSessionManager {

	/**
	 * Begins the impersonation of a given user in the current
	 * {@link HttpSession}.
	 *
	 * @param targetUser
	 *            the user to impersonate (never null)
	 * @param rationale
	 *            a convincing rationale to impersonate the given user (never
	 *            null)
	 */
	public static void beginUserImpersonation(AGUser targetUser, String rationale) {

		Objects.requireNonNull(targetUser);
		Objects.requireNonNull(rationale);

		HttpSession httpSession = getCurrentHttpSession();
		synchronized (httpSession) {
			if (getUserImpersonationState(httpSession).isPresent()) {
				throw new SofticarUserException(CoreI18n.YOU_ALREADY_IMPERSONATE_ANOTHER_USER);
			}

			AGUserImpersonationState state = new UserImpersonationStarter(targetUser, rationale).start();
			SofticarAjaxSession ajaxSession = createAndReplaceSession(httpSession, targetUser);
			ajaxSession.setUserImpersonationState(state);
		}
	}

	/**
	 * Terminates the active user impersonation in the current
	 * {@link HttpSession}.
	 *
	 * @throws SofticarUserException
	 *             if there is no active user impersonation
	 */
	public static void terminateUserImpersonation() {

		HttpSession httpSession = getCurrentHttpSession();
		synchronized (httpSession) {
			AGUserImpersonationState state = getUserImpersonationState(httpSession)//
				.orElseThrow(() -> new SofticarUserException(CoreI18n.CURRENTLY_NOT_IMPERSONATING_A_USER));

			state.setFinishedAt(DayTime.now());
			state.save();

			createAndReplaceSession(httpSession, state.getSourceUser());
		}
	}

	/**
	 * In case a user is being impersonated in the current {@link HttpSession},
	 * this method returns the user who impersonates another user.
	 *
	 * @return the user who impersonates another user in the current session, or
	 *         {@link Optional#empty()} if no impersonation is active
	 */
	public static Optional<AGUser> getImpersonatingUserFromCurrentSession() {

		return getImpersonatingUser(getCurrentHttpSession());
	}

	/**
	 * In case a user is being impersonated in the given {@link HttpSession},
	 * this method returns the user who impersonates another user.
	 *
	 * @param httpSession
	 *            the session to check (never null)
	 * @return the user who impersonates another user in the given
	 *         {@link HttpSession}, or {@link Optional#empty()} if no
	 *         impersonation is active
	 */
	public static Optional<AGUser> getImpersonatingUser(HttpSession httpSession) {

		return getUserImpersonationState(httpSession).map(it -> it.getSourceUser());
	}

	/**
	 * Checks whether a user is being impersonated in the given
	 * {@link HttpSession}.
	 *
	 * @param httpSession
	 *            the session to check (never null)
	 * @return <i>true</i> if a user is being impersonated in the given
	 *         {@link HttpSession}; <i>false</i> otherwise
	 */
	public static boolean isUserImpersonationActive(HttpSession httpSession) {

		return getUserImpersonationState(httpSession).isPresent();
	}

	private static SofticarAjaxSession createAndReplaceSession(HttpSession httpSession, AGUser user) {

		synchronized (httpSession) {
			SofticarAjaxSession existingAjaxSession = SofticarAjaxSession//
				.getInstance(httpSession)
				.orElseThrow(() -> new SofticarDeveloperException("Failed to find existing session object."));

			SofticarAjaxSession newSofticarSession = new SofticarAjaxSession(existingAjaxSession.getSession(), user);
			new AjaxSessionAttributeManager(httpSession).setInstance(SofticarAjaxSession.class, newSofticarSession);
			return newSofticarSession;
		}
	}

	private static Optional<AGUserImpersonationState> getUserImpersonationState(HttpSession httpSession) {

		return SofticarAjaxSession//
			.getInstance(httpSession)
			.flatMap(SofticarAjaxSession::getUserImpersonationState);
	}

	private static HttpSession getCurrentHttpSession() {

		return AjaxDocument//
			.getCurrentDocument()
			.get()
			.getHttpSession();
	}
}
