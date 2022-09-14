package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.login.AGUserLoginLog;
import com.softicar.platform.core.module.user.login.failure.AGUserLoginFailureLog;
import com.softicar.platform.core.module.user.login.failure.type.AGUserLoginFailureTypeEnum;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.core.module.user.password.policy.SofticarPasswordPolicy;
import com.softicar.platform.core.module.user.rule.ip.AGUserAllowedIpRule;
import com.softicar.platform.core.module.web.servlet.HttpServletRequests;

/**
 * Implements HTTP basic authentication.
 *
 * @author Oliver Richers
 */
class PageServiceLoginAuthenticator {

	// TODO these constants should be configurable
	public static final int MAXIMUM_LOGINS = 10;
	public static final int MAXIMUM_LOGINS_PERIOD = 60;
	public static final int DEFAULT_MAXIMUM_LOGIN_FAILURES = 3;
	public static final int DEFAULT_LOGIN_FAILURE_TIMEOUT = 60;
	private final IAjaxRequest request;
	private final String username;
	private final String password;
	private int maximumLoginFailures;
	private int loginFailureTimeout;

	public PageServiceLoginAuthenticator(IAjaxRequest request, String username, String password) {

		this.request = request;
		this.username = username;
		this.password = password;
		this.maximumLoginFailures = DEFAULT_MAXIMUM_LOGIN_FAILURES;
		this.loginFailureTimeout = DEFAULT_LOGIN_FAILURE_TIMEOUT;
	}

	public PageServiceLoginAuthenticator setMaximumLoginFailures(int maximumFailureCount) {

		this.maximumLoginFailures = maximumFailureCount;
		return this;
	}

	public PageServiceLoginAuthenticator setLoginFailureTimeout(int seconds) {

		this.loginFailureTimeout = seconds;
		return this;
	}

	public AGUser authenticate() {

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
			throw new PageServiceLoginExceptionTooManyFailedLogins(loginFailureTimeout);
		}

		// load user and check if user exists
		AGUser user = AGUser.loadByLoginName(username);
		if (user == null) {
			logFailure(AGUserLoginFailureTypeEnum.UNKOWN_USER, username);
			throw new PageServiceLoginExceptionWrongUsernameOrPassword();
		}

		// check client IP
		String clientAddress = getClientAddress();
		if (!isLegalClientAddress(clientAddress, user.getAllowedIpRule())) {
			logFailure(AGUserLoginFailureTypeEnum.ILLEGAL_IP, username);
			throw new PageServiceLoginExceptionIllegalClientAddress(clientAddress);
		}

		// load active password
		AGUserPassword activePassword = user.getActivePassword();
		if (activePassword == null) {
			logFailure(AGUserLoginFailureTypeEnum.NO_ACTIVE_PASSWORD, username);
			throw new PageServiceLoginExceptionWrongUsernameOrPassword();
		}

		// verify password
		if (!activePassword.verifyPassword(password)) {
			logFailure(AGUserLoginFailureTypeEnum.WRONG_PASSWORD, username);
			throw new PageServiceLoginExceptionWrongUsernameOrPassword();
		}

		// verify user account is enabled
		if (!user.isActive()) {
			logFailure(AGUserLoginFailureTypeEnum.DISABLED_USER, username);
			throw new PageServiceLoginExceptionWrongUsernameOrPassword();
		}

		// check for too many logins
		int logins = AGUserLoginLog//
			.createSelect()
			.where(AGUserLoginLog.USER.equal(user))
			.where(AGUserLoginLog.LOGIN_AT.greaterEqual(DayTime.now().minusSeconds(MAXIMUM_LOGINS_PERIOD)))
			.count();
		if (logins >= MAXIMUM_LOGINS) {
			logFailure(AGUserLoginFailureTypeEnum.TOO_MANY_LOGINS, username);
			throw new PageServiceLoginExceptionTooManyLogins(MAXIMUM_LOGINS_PERIOD);
		}

		// check for maintenance windows
		if (AGMaintenanceWindow.isMaintenanceInProgress()) {
			if (!CorePermissions.ADMINISTRATION.test(AGCoreModuleInstance.getInstance(), user)) {
				logFailure(AGUserLoginFailureTypeEnum.MAINTENANCE_IN_PROGRESS, username);
				throw new PageServiceLoginExceptionMaintenanceInProgress();
			}
		}

		// create login log
		logLogin(user, activePassword);
		updatePasswordPolicyCompliance(password, activePassword);

		return user;
	}

	private String getClientAddress() {

		return HttpServletRequests.getClientAddress(request.getHttpRequest());
	}

	private boolean isLegalClientAddress(String clientAddress, AGUserAllowedIpRule allowedIpRule) {

		return allowedIpRule == null || allowedIpRule.isActive() && allowedIpRule.matches(clientAddress);
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
