package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.request.CurrentAjaxRequest;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.core.module.ajax.logging.AjaxLogging;
import com.softicar.platform.core.module.ajax.login.OneTimePasswordChallenge;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.dom.document.CurrentDomDocument;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;

class PageServiceLoginExecutor {

	private final IAjaxRequest request;
	private final HttpSession session;

	public PageServiceLoginExecutor() {

		this(CurrentAjaxRequest.get(CurrentDomDocument.get()).get());
	}

	PageServiceLoginExecutor(IAjaxRequest request) {

		this.request = request;
		this.session = request.getHttpSession();
	}

	/**
	 * Authenticates the user with the given username and password.
	 * <p>
	 * If two-factor authentication is required for the user, a one-time
	 * password is sent to the user, and the login must be completed with
	 * {@link #completeLogin}. Otherwise, the user is logged in immediately.
	 *
	 * @param passwordIndex
	 *            the index for the one-time password, if one is sent
	 * @return the {@link OneTimePasswordChallenge} if two-factor
	 *         authentication is required; an empty {@link Optional} if the
	 *         user was logged in
	 */
	public Optional<OneTimePasswordChallenge> executeLogin(String username, String password, int passwordIndex) {

		AGUser user = new PageServiceLoginAuthenticator(request, username, password).authenticate();

		if (user.isTwoFactorAuthenticationRequired()) {
			return Optional.of(OneTimePasswordChallenge.sendTo(user, passwordIndex));
		} else {
			login(user);
			return Optional.empty();
		}
	}

	/**
	 * Completes a login with two-factor authentication.
	 * <p>
	 * The one-time password of the given {@link OneTimePasswordChallenge} is
	 * invalidated, even if the given password was wrong.
	 *
	 * @throws PageServiceLoginExceptionWrongOneTimePassword
	 *             if the one-time password is wrong or expired
	 */
	public void completeLogin(OneTimePasswordChallenge challenge, String oneTimePassword) {

		if (challenge.validate(oneTimePassword)) {
			login(challenge.getUser());
		} else {
			throw new PageServiceLoginExceptionWrongOneTimePassword();
		}
	}

	private void login(AGUser user) {

		new AjaxSessionAttributeManager(session).setInstance(SofticarAjaxSession.class, createSession(user));

		CurrentUser.set(user);
		CurrentLocale.set(user.getLocale());
	}

	private SofticarAjaxSession createSession(AGUser user) {

		return new SofticarAjaxSession(AjaxLogging.logSessionCreation(request.getHttpRequest(), user), user);
	}
}
