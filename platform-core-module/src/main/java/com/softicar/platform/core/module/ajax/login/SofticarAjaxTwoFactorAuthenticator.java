package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.locale.LocaleScope;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.user.AGUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implements two-factor authentication.
 *
 * @author Oliver Richers
 */
class SofticarAjaxTwoFactorAuthenticator {

	protected static final String ONE_TIME_PASSWORD_REQUEST_PARAMETER = "otp";
	private final AGUser user;
	private final HttpServletRequest request;
	private final HttpServletResponse response;

	/**
	 * Constructs this object using the given user, request and response
	 * objects.
	 *
	 * @param user
	 *            the {@link AGUser} trying to log in (never null)
	 * @param request
	 *            the HTTP request object (never null)
	 * @param response
	 *            the HTTP response object (never null)
	 */
	public SofticarAjaxTwoFactorAuthenticator(AGUser user, HttpServletRequest request, HttpServletResponse response) {

		this.user = Objects.requireNonNull(user);
		this.request = Objects.requireNonNull(request);
		this.response = Objects.requireNonNull(response);
	}

	/**
	 * Validates the one-time password and removes it.
	 * <p>
	 * The password supplied by the user is expected to be given in the request
	 * parameter {@link #ONE_TIME_PASSWORD_REQUEST_PARAMETER}.
	 *
	 * @return <i>true</i> if the supplied password is valid; <i>false</i>
	 *         otherwise
	 */
	public boolean validateOneTimePassword() {

		return OneTimePasswordSessionAttribute//
			.getInstance(request.getSession())
			.validatePassword(getOneTimePasswordFromRequest());
	}

	public void initiateTwoFactorAuthentication() {

		OneTimePassword password = OneTimePasswordSessionAttribute//
			.getInstance(request.getSession())
			.generateAndStorePasswort();

		try (var scope = new LocaleScope(user.getLocale())) {
			sendEmail(password);
			printTokenInputPage(password.getIndex(), response);
		}
	}

	private String getOneTimePasswordFromRequest() {

		return request.getParameter(ONE_TIME_PASSWORD_REQUEST_PARAMETER);
	}

	private void sendEmail(OneTimePassword password) {

		BufferedEmailFactory//
			.createNoReplyEmail()
			.setSubject(CoreI18n.ONE_TIME_PASSWORD)
			.addToRecipient(user)
			.setContent(//
				new DisplayString()
					.append(
						CoreI18n.ONE_TIME_PASSWORD_ARG1_TO_LOG_IN_TO_YOUR_ARG2_ACCOUNT_ARG3
							.toDisplay("#" + password.getIndex(), AGCoreModuleInstance.getSystemIdentifier(), user.getLoginName()))
					.append("\r\n\r\n")
					.append(password.getText())
					.toString(),
				EmailContentType.PLAIN)
			.submit();
	}

	private void printTokenInputPage(int passwordCounter, HttpServletResponse response) {

		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		response.setContentType(MimeType.TEXT_HTML.getIdentifier());
		try (PrintWriter writer = new PrintWriter(response.getOutputStream())) {
			writer.print(getTokenInputPageHtml(passwordCounter));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private String getTokenInputPageHtml(int passwordCounter) {

		return new DisplayString()//
			.setSeparator("\r\n")
			.append(CoreI18n.TWO_FACTOR_AUTHENTICATION_IS_ACTIVE)
			.append(CoreI18n.ARG1_ONE_TIME_PASSWORD_HAS_BEEN_SENT_TO_YOUR_EMAIL_ADDRESS.toDisplay(AGCoreModuleInstance.getSystemIdentifier()))
			.append("<br>")
			.append("<form method='post'>")
			.append(CoreI18n.ONE_TIME_PASSWORD.concat(" #" + passwordCounter + ":"))
			.append("<input type='text' name='otp'>")
			.append("<input type='submit'>")
			.append("</form>")
			.toString();
	}
}
