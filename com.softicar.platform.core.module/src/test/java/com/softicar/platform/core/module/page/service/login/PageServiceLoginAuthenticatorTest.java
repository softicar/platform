package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.login.AGUserLoginLog;
import com.softicar.platform.core.module.user.login.failure.AGUserLoginFailureLog;
import com.softicar.platform.core.module.user.login.failure.type.AGUserLoginFailureTypeEnum;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.core.module.user.rule.ip.AGUserAllowedIpRule;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;
import org.mockito.Mockito;

public class PageServiceLoginAuthenticatorTest extends AbstractDbTest implements CoreModuleTestFixtureMethods {

	private static final String USERNAME = "john.doe";
	private static final String PASSWORD = "secure";
	private static final String CLIENT_ADDRESS = "1.2.3.4";
	private final IAjaxRequest request;
	private final AGUser user;
	private final AGUserPassword password;

	public PageServiceLoginAuthenticatorTest() {

		this.request = Mockito.mock(IAjaxRequest.class);
		Mockito.when(request.getRemoteAddr()).thenReturn(CLIENT_ADDRESS);

		this.user = insertUser("John", "Doe", USERNAME);
		this.password = insertPassword(user, PASSWORD);
	}

	@Test
	public void testWithTooManyLoginFailures() {

		int maximumFailureCount = PageServiceLoginAuthenticator.DEFAULT_MAXIMUM_LOGIN_FAILURES;
		for (int i = 0; i < maximumFailureCount; i++) {
			assertThrows(PageServiceLoginExceptionWrongUsernameOrPassword.class, () -> authenticate(USERNAME, ""));
		}

		// please note we are using the correct username and password now
		assertThrows(PageServiceLoginExceptionTooManyFailedLogins.class, () -> authenticate(USERNAME, PASSWORD));
		assertCount(maximumFailureCount + 1, AGUserLoginFailureLog.TABLE.loadAll());
	}

	@Test
	public void testWithUnknownUser() {

		assertThrows(PageServiceLoginExceptionWrongUsernameOrPassword.class, () -> authenticate("unknown", ""));
		assertFailureLog(AGUserLoginFailureTypeEnum.UNKOWN_USER, "unknown");
	}

	@Test
	public void testWithIllegalClientAddress() {

		AGUserAllowedIpRule rule = new AGUserAllowedIpRule()//
			.setActive(true)
			.setName("Internal")
			.setAddresses("192.168.0.0/16")
			.save();
		user.setAllowedIpRule(rule).save();

		assertThrows(PageServiceLoginExceptionIllegalClientAddress.class, () -> authenticate(USERNAME, PASSWORD));
		assertFailureLog(AGUserLoginFailureTypeEnum.ILLEGAL_IP, USERNAME);
	}

	@Test
	public void testWithInactivePassword() {

		password.setActive(false).save();

		assertThrows(PageServiceLoginExceptionWrongUsernameOrPassword.class, () -> authenticate(USERNAME, PASSWORD));
		assertFailureLog(AGUserLoginFailureTypeEnum.NO_ACTIVE_PASSWORD, USERNAME);
	}

	@Test
	public void testWithWrongPassword() {

		assertThrows(PageServiceLoginExceptionWrongUsernameOrPassword.class, () -> authenticate(USERNAME, "wrong"));
		assertFailureLog(AGUserLoginFailureTypeEnum.WRONG_PASSWORD, USERNAME);
	}

	@Test
	public void testWithInactiveUser() {

		user.setActive(false).save();

		assertThrows(PageServiceLoginExceptionWrongUsernameOrPassword.class, () -> authenticate(USERNAME, PASSWORD));
		assertFailureLog(AGUserLoginFailureTypeEnum.DISABLED_USER, USERNAME);
	}

	@Test
	public void testWithTooManyLogins() {

		int maximumLoginCount = PageServiceLoginAuthenticator.MAXIMUM_LOGINS;
		for (int i = 0; i < maximumLoginCount; i++) {
			authenticate(USERNAME, PASSWORD);
		}

		assertThrows(PageServiceLoginExceptionTooManyLogins.class, () -> authenticate(USERNAME, PASSWORD));
		assertFailureLog(AGUserLoginFailureTypeEnum.TOO_MANY_LOGINS, USERNAME);
	}

	@Test
	public void testWithValidUsernameAndPassword() {

		AGUser authenticatedUser = authenticate(USERNAME, PASSWORD);

		assertSame(user, authenticatedUser);
		assertLoginLog(user, password);
	}

	private AGUser authenticate(String username, String password) {

		return new PageServiceLoginAuthenticator(request, username, password).authenticate();
	}

	private static void assertFailureLog(AGUserLoginFailureTypeEnum typeEnum, String username) {

		AGUserLoginFailureLog failureLog = assertOne(AGUserLoginFailureLog.TABLE.loadAll());
		assertEquals(typeEnum.getRecord(), failureLog.getType());
		assertEquals(username, failureLog.getUsername());
		assertEquals(CLIENT_ADDRESS, failureLog.getServerIpAddress());
		assertEquals(DayTime.now(), failureLog.getLoginAt());
	}

	private static void assertLoginLog(AGUser user, AGUserPassword password) {

		AGUserLoginLog loginLog = assertOne(AGUserLoginLog.TABLE.loadAll());
		assertEquals(user, loginLog.getUser());
		assertEquals(password, loginLog.getPassword());
		assertEquals(DayTime.now(), loginLog.getLoginAt());
	}
}
