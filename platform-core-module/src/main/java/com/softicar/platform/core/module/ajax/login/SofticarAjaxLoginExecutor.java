package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.ajax.session.AjaxSessionAttributeManager;
import com.softicar.platform.core.module.ajax.logging.AjaxLogging;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.web.service.WebServiceUtils;
import java.util.Objects;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * This class handles the login.
 *
 * @author Oliver Richers
 */
public class SofticarAjaxLoginExecutor {

	private final HttpServletRequest request;
	private final HttpServletResponse response;

	/**
	 * Constructs this object using the given request and response objects.
	 *
	 * @param request
	 *            the HTTP request object (never null)
	 * @param response
	 *            the HTTP response object (never null)
	 */
	public SofticarAjaxLoginExecutor(HttpServletRequest request, HttpServletResponse response) {

		this.request = Objects.requireNonNull(request);
		this.response = Objects.requireNonNull(response);
	}

	/**
	 * Executes the user login.
	 * <p>
	 * This method assumes that the {@link HttpSession} does not possess a
	 * {@link SofticarAjaxSession} object, yet.
	 * <p>
	 * If the user is not yet authenticated, this method initiates the necessary
	 * authentications through the given {@link HttpServletResponse} object and
	 * returns <i>null</i>.
	 * <p>
	 * If the user is already fully authenticated, e.g. over basic HTTP
	 * authentication or two-factor authentication, this methods creates a new
	 * {@link SofticarAjaxSession} object and adds it to the
	 * {@link HttpSession}.
	 * <p>
	 * If basic HTTP authentication was sufficient, this method returns the new
	 * {@link SofticarAjaxSession} object immediately.
	 * <p>
	 * If, on the other hand, two-factor authentication was necessary, a
	 * {@link HttpServletResponse#SC_SEE_OTHER} response is sent via the
	 * {@link HttpServletResponse} object to change the request from POST method
	 * to GET method. This is necessary to avoid a double-submit of the
	 * two-factor authentication as soon as the user reloads the page. See <a
	 * href=https://en.wikipedia.org/wiki/Post/Redirect/Get>PRG Pattern</a> for
	 * more details. In this case, the method will return <i>null</i>.
	 *
	 * @return the new {@link SofticarAjaxSession} if the user is authenticated,
	 *         an empty {@link Optional} otherwise
	 */
	public Optional<SofticarAjaxSession> executeLogin() {

		// basic HTTP authentication
		AGUser user = new SofticarBasicAuthenticator(request, response).authenticate();
		if (user == null) {
			initiateBasicAuthentication(response);
			return Optional.empty();
		}

		// optional two-factor authentication
		if (user.isTwoFactorAuthenticationRequired()) {
			handleTwoFactorAuthentication(user);
			return Optional.empty();
		}

		// create new session
		return Optional.of(createAndAttachAjaxSession(user));
	}

	private void initiateBasicAuthentication(HttpServletResponse response) {

		if (!response.isCommitted()) {
			WebServiceUtils.sendAuthorizationRequest(response, "Basic", "Internal area");
		}
	}

	private void handleTwoFactorAuthentication(AGUser user) {

		SofticarAjaxTwoFactorAuthenticator twoFactorAuthenticator = new SofticarAjaxTwoFactorAuthenticator(user, request, response);
		if (twoFactorAuthenticator.validateOneTimePassword()) {
			createAndAttachAjaxSession(user);
			WebServiceUtils.sendSeeOtherResponse(request, response);
		} else {
			twoFactorAuthenticator.initiateTwoFactorAuthentication();
		}
	}

	private SofticarAjaxSession createAndAttachAjaxSession(AGUser user) {

		return new AjaxSessionAttributeManager(request.getSession())//
			.getOrAddInstance(SofticarAjaxSession.class, () -> createSession(request, user));
	}

	private SofticarAjaxSession createSession(HttpServletRequest request, AGUser user) {

		return new SofticarAjaxSession(AjaxLogging.logSessionCreation(request, user), user);
	}
}
