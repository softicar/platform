package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.ajax.simple.SimpleServletRequest;
import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.core.i18n.CurrentLanguageTranslator;
import com.softicar.platform.common.core.i18n.ILanguageTranslator;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.common.string.unicode.Utf8Convering;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.login.AGUserLoginLog;
import com.softicar.platform.core.module.user.login.failure.AGUserLoginFailureLog;
import com.softicar.platform.core.module.user.login.failure.type.AGUserLoginFailureTypeEnum;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import org.junit.Test;

public class SofticarBasicAuthenticatorTest extends AbstractCoreTest {

	private final SimpleServletRequest request;
	private final SimpleServletResponse response;
	private AGUser user;
	private AGUser authenticatedUser;
	private List<AGUserLoginLog> loginLogs;
	private List<AGUserLoginFailureLog> failureLogs;

	public SofticarBasicAuthenticatorTest() {

		this.request = new SimpleServletRequest();
		this.response = new SimpleServletResponse() {

			private boolean committed = false;

			@Override
			public void flushBuffer() {

				this.committed = true;
			}

			@Override
			public boolean isCommitted() {

				return committed;
			}
		};
	}

	@Test
	public void testWithoutHeader() {

		executeAuthentication();

		assertNull(authenticatedUser);
		assertTrue(failureLogs.isEmpty());
	}

	@Test
	public void testMalformedHeader() {

		executeAuthentication("foo");

		assertNull(authenticatedUser);
		assertEquals(1, failureLogs.size());
		assertEquals(AGUserLoginFailureTypeEnum.MALFORMED_REQUEST.getRecord(), failureLogs.get(0).getType());
	}

	@Test
	public void testUnknownUser() {

		executeAuthentication("foo:bar");

		assertNull(authenticatedUser);
		assertEquals(1, failureLogs.size());
		assertEquals(AGUserLoginFailureTypeEnum.UNKOWN_USER.getRecord(), failureLogs.get(0).getType());
		assertEquals("foo", failureLogs.get(0).getUsername());
	}

	@Test
	public void testUserWithoutPassword() {

		this.user = insertUser("foo");
		executeAuthentication("foo:bar");

		assertNull(authenticatedUser);
		assertEquals(1, failureLogs.size());
		assertEquals(AGUserLoginFailureTypeEnum.NO_ACTIVE_PASSWORD.getRecord(), failureLogs.get(0).getType());
		assertEquals("foo", failureLogs.get(0).getUsername());
	}

	@Test
	public void testWrongPassword() {

		this.user = insertUserWithPassword("foo", "baz");
		executeAuthentication("foo:bar");

		assertNull(authenticatedUser);
		assertEquals(1, failureLogs.size());
		assertEquals(AGUserLoginFailureTypeEnum.WRONG_PASSWORD.getRecord(), failureLogs.get(0).getType());
		assertEquals("foo", failureLogs.get(0).getUsername());
	}

	@Test
	public void testTooManyLoginFailures() {

		this.user = insertUserWithPassword("foo", "baz");

		// execute too many failed logins
		for (int i = 0; i < SofticarBasicAuthenticator.DEFAULT_MAXIMUM_LOGIN_FAILURES; i++) {
			executeAuthentication("foo:bar");
		}

		// check error type of failures
		for (AGUserLoginFailureLog log: AGUserLoginFailureLog.TABLE.loadAll()) {
			assertEquals(AGUserLoginFailureTypeEnum.WRONG_PASSWORD.getRecord(), log.getType());
		}

		// now try correct login
		executeAuthentication("foo:baz");

		// check that login failed
		assertNull(authenticatedUser);
		assertEquals(SofticarBasicAuthenticator.DEFAULT_MAXIMUM_LOGIN_FAILURES + 1, failureLogs.size());
		assertResponseContains("too many", "please wait");

		// check error type of last failure log
		AGUserLoginFailureLog lastLog = AGUserLoginFailureLog.createSelect().orderDescendingBy(AGUserLoginFailureLog.ID).getFirst();
		assertEquals(AGUserLoginFailureTypeEnum.TOO_MANY_FAILURES.getRecord(), lastLog.getType());
	}

	@Test
	public void testTooManyLoginFailuresWithTranslation() {

		this.user = insertUserWithPassword("foo", "baz");

		// prepare translation
		TestTranslator translator = new TestTranslator()//
			.add(CoreI18n.TOO_MANY_FAILED_LOGIN_ATTEMPTS, LanguageEnum.GERMAN, "Zuviele blablabla")
			.add(CoreI18n.PLEASE_WAIT_FOR_ARG1_SECONDS, LanguageEnum.GERMAN, "Bitte warten blabla %s");
		CurrentLanguageTranslator.set(translator);
		user.setLocalization(insertLocalizationPresetGermany()).save();

		// execute too many failed logins
		for (int i = 0; i < SofticarBasicAuthenticator.DEFAULT_MAXIMUM_LOGIN_FAILURES; i++) {
			executeAuthentication("foo:bar");
		}

		// now try correct login
		executeAuthentication("foo:baz");

		// check the error message is translated to the user's preferred language
		assertResponseContains("Zuviele", "Bitte warten");
	}

	@Test
	public void testTooManyLogins() {

		this.user = insertUserWithPassword("foo", "bar");

		// execute maximum logins
		for (int i = 0; i < SofticarBasicAuthenticator.MAXIMUM_LOGINS; i++) {
			executeAuthentication("foo:bar");
		}
		assertTrue(AGUserLoginFailureLog.TABLE.loadAll().isEmpty());
		assertNotNull(authenticatedUser);

		// execute one more login
		executeAuthentication("foo:bar");
		assertNull(authenticatedUser);
		assertEquals(1, failureLogs.size());
		assertEquals(AGUserLoginFailureTypeEnum.TOO_MANY_LOGINS.getRecord(), failureLogs.get(0).getType());
		assertResponseContains("too many", "please wait");
	}

	@Test
	public void testTooManyLoginsWithTranslation() {

		this.user = insertUserWithPassword("foo", "bar");

		// prepare translation
		user.setLocalization(insertLocalizationPresetGermany()).save();

		// execute maximum logins
		for (int i = 0; i < SofticarBasicAuthenticator.MAXIMUM_LOGINS; i++) {
			executeAuthentication("foo:bar");
		}

		// execute one more login
		executeAuthentication("foo:bar");

		// check the error message is translated to the user's preferred language
		assertResponseContains("Zuviele", "Bitte warten");
	}

	@Test
	public void testCorrectLogin() {

		this.user = insertUserWithPassword("foo", "bar");
		executeAuthentication("foo:bar");

		assertSame(user, authenticatedUser);
		assertTrue(failureLogs.isEmpty());
		assertEquals(1, loginLogs.size());
		assertSame(user, loginLogs.get(0).getUser());
		assertSame(AGUserPassword.getActive(user), loginLogs.get(0).getPassword());
	}

	private AGUser insertUserWithPassword(String name, String password) {

		return insertPassword(insertUser(name), password).getUser();
	}

	private void executeAuthentication(String credentials) {

		request.setHeader("Authorization", "Basic " + getBase64Encoded(credentials));
		executeAuthentication();
	}

	private void executeAuthentication() {

		this.authenticatedUser = new SofticarBasicAuthenticator(request, response).authenticate();
		this.loginLogs = AGUserLoginLog.TABLE.loadAll();
		this.failureLogs = AGUserLoginFailureLog.TABLE.loadAll();
	}

	private String getBase64Encoded(String text) {

		return new String(Base64.getEncoder().encode(text.getBytes(Charsets.UTF8)));
	}

	private void assertResponseContains(String...texts) {

		String message = Utf8Convering.fromUtf8(response.getContent());
		assertNotNull(message);
		for (String text: texts) {
			assertTrue(//
				String.format("response message '%s' does not contain '%s'", message.trim(), text),
				message.toLowerCase().contains(text.toLowerCase()));
		}
	}

	private static class TestTranslator implements ILanguageTranslator {

		private final Map<String, Map<LanguageEnum, String>> translationMap;

		public TestTranslator() {

			this.translationMap = new TreeMap<>();
		}

		public TestTranslator add(II18nKey key, LanguageEnum language, String translation) {

			translationMap.computeIfAbsent(key.toEnglish(), dummy -> new TreeMap<>()).put(language, translation);
			return this;
		}

		@Override
		public String translate(LanguageEnum language, String text) {

			return Optional.ofNullable(translationMap.get(text)).map(it -> it.get(language)).orElse(text);
		}
	}
}
