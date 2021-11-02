package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.LanguageScope;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.login.AGUserLoginLog;
import com.softicar.platform.core.module.user.login.failure.AGUserLoginFailureLog;
import com.softicar.platform.core.module.user.login.failure.type.AGUserLoginFailureTypeEnum;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.core.module.user.rule.ip.AGUserAllowedIpRule;
import com.softicar.platform.core.module.web.service.WebServiceUtils;
import com.softicar.platform.core.module.web.servlet.HttpServletRequests;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Implements HTTP basic authentication.
 *
 * @author Oliver Richers
 */
class SofticarBasicAuthenticator {

	public static final int MAXIMUM_LOGINS = 10;
	public static final int MAXIMUM_LOGINS_PERIOD = 60;
	public static final int DEFAULT_MAXIMUM_LOGIN_FAILURES = 3;
	public static final int DEFAULT_LOGIN_FAILURE_TIMEOUT = 60;
	private static final String CREDENTIAL_PREFIX = "Basic ";
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private int maximumLoginFailures;
	private int loginFailureTimeout;

	public SofticarBasicAuthenticator(HttpServletRequest request, HttpServletResponse response) {

		this.request = request;
		this.response = response;
		this.maximumLoginFailures = DEFAULT_MAXIMUM_LOGIN_FAILURES;
		this.loginFailureTimeout = DEFAULT_LOGIN_FAILURE_TIMEOUT;
	}

	public SofticarBasicAuthenticator setMaximumLoginFailures(int maximumFailureCount) {

		this.maximumLoginFailures = maximumFailureCount;
		return this;
	}

	public SofticarBasicAuthenticator setLoginFailureTimeout(int seconds) {

		this.loginFailureTimeout = seconds;
		return this;
	}

	public AGUser authenticate() {

		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith(CREDENTIAL_PREFIX)) {
			String credentials = header.substring(CREDENTIAL_PREFIX.length());
			return checkCredentials(credentials.trim());
		} else {
			return null;
		}
	}

	private AGUser checkCredentials(String credentials) {

		// Note that ISO_8859_1 is default (the new character set parameter is still a draft)
		byte[] decodedBytes = Base64.getDecoder().decode(credentials);
		String decoded = new String(decodedBytes, Charsets.ISO_8859_1);

		// split credential string into user name and password
		String[] parts = decoded.split(":", 2);
		if (parts.length != 2) {
			logFailure(AGUserLoginFailureTypeEnum.MALFORMED_REQUEST, "");
			return null;
		}
		String username = parts[0];
		String password = parts[1];

		// load user and language
		AGUser user = AGUser.loadByLoginName(username);
		LanguageEnum language = getUserLanguageOrEnglish(user);

		// check for too many login failures
		int failedLogins = AGUserLoginFailureLog//
			.createSelect()
			.where(AGUserLoginFailureLog.USERNAME.equal(username))
			.where(AGUserLoginFailureLog.LOGIN_AT.greaterEqual(DayTime.now().minusSeconds(loginFailureTimeout)))
			.count();
		if (failedLogins >= maximumLoginFailures) {
			// Please note that we must fail here even if the user now entered the correct password!
			// Otherwise this whole concept would be ineffective against brute-force attacks.
			logFailure(AGUserLoginFailureTypeEnum.TOO_MANY_FAILURES, username);
			printFailureAnswerAndFlush(//
				WebServiceUtils.getTooManyRequestsHttpCode(),
				CoreI18n.TOO_MANY_FAILED_LOGIN_ATTEMPTS.concatSpace().concat(CoreI18n.PLEASE_WAIT_FOR_ARG1_SECONDS.toDisplay(loginFailureTimeout)),
				language);
			return null;
		}

		// check if user exists
		if (user == null) {
			logFailure(AGUserLoginFailureTypeEnum.UNKOWN_USER, username);
			return null;
		}

		// check client IP
		String clientAddress = getClientAddress();
		if (!isLegalClientAddress(clientAddress, user.getAllowedIpRule())) {
			logFailure(AGUserLoginFailureTypeEnum.ILLEGAL_IP, username);
			printFailureAnswerAndFlush(//
				HttpServletResponse.SC_FORBIDDEN,
				CoreI18n.YOU_ARE_NOT_ALLOWED_TO_LOG_IN_FROM_THE_FOLLOWING_IP_ADDRESS_ARG1.toDisplay(clientAddress),
				language);
			return null;
		}

		// load active password
		AGUserPassword activePassword = user.getActivePassword();
		if (activePassword == null) {
			logFailure(AGUserLoginFailureTypeEnum.NO_ACTIVE_PASSWORD, username);
			return null;
		}

		// verify password
		if (!activePassword.verifyPassword(password)) {
			logFailure(AGUserLoginFailureTypeEnum.WRONG_PASSWORD, username);
			return null;
		}

		// verify user account is enabled
		if (!user.isActive()) {
			logFailure(AGUserLoginFailureTypeEnum.DISABLED_USER, username);
			return null;
		}

		// check for too many logins
		int logins = AGUserLoginLog//
			.createSelect()
			.where(AGUserLoginLog.USER.equal(user))
			.where(AGUserLoginLog.LOGIN_AT.greaterEqual(DayTime.now().minusSeconds(MAXIMUM_LOGINS_PERIOD)))
			.count();
		if (logins >= MAXIMUM_LOGINS) {
			logFailure(AGUserLoginFailureTypeEnum.TOO_MANY_LOGINS, username);
			printFailureAnswerAndFlush(//
				WebServiceUtils.getTooManyRequestsHttpCode(),
				CoreI18n.TOO_MANY_FAILED_LOGIN_ATTEMPTS.concatSpace().concat(CoreI18n.PLEASE_WAIT_FOR_ARG1_SECONDS.toDisplay(MAXIMUM_LOGINS_PERIOD)),
				language);
			return null;
		}

		// create login log
		logLogin(user, activePassword);
		updatePasswordPolicyCompliance(password, activePassword);

		return user;
	}

	private String getClientAddress() {

		return HttpServletRequests.getClientAddress(request);
	}

	private boolean isLegalClientAddress(String clientAddress, AGUserAllowedIpRule allowedIpRule) {

		return allowedIpRule == null || allowedIpRule.isActive() && allowedIpRule.matches(clientAddress);
	}

	private LanguageEnum getUserLanguageOrEnglish(AGUser user) {

		return Optional.ofNullable(user).map(AGUser::getLanguageEnum).orElse(LanguageEnum.ENGLISH);
	}

	private void printFailureAnswerAndFlush(int statusCode, IDisplayString message, LanguageEnum language) {

		try (LanguageScope languageScope = new LanguageScope(language)) {
			WebServiceUtils.sendPlainTextResponse(response, statusCode, message);
			response.flushBuffer();
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private void logFailure(AGUserLoginFailureTypeEnum type, String username) {

		AGUserLoginFailureLog log = new AGUserLoginFailureLog();
		log.setType(type.getRecord());
		log.setUsername(username);
		log.setServerIpAddress(getClientAddress());
		log.setLoginAt(DayTime.now());
		log.save();
	}

	private void logLogin(AGUser user, AGUserPassword activePassword) {

		AGUserLoginLog log = new AGUserLoginLog();
		log.setUser(user);
		log.setPassword(activePassword);
		log.setLoginAt(DayTime.now());
		log.save();
	}

	private void updatePasswordPolicyCompliance(String password, AGUserPassword activePassword) {

		boolean fulfilled = SofticarPasswordPolicy.get().isFulfilled(password);
		activePassword.setPolicyFulfilled(fulfilled);
		activePassword.setLastLoginAt(DayTime.now());
		activePassword.save();
	}
}
