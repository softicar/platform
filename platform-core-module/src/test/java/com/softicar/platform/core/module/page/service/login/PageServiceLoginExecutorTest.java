package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.simple.SimpleHttpSession;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.ajax.login.OneTimePasswordChallenge;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.password.policy.AGPasswordPolicy;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class PageServiceLoginExecutorTest extends AbstractCoreTest {

	private static final String USERNAME = "john.doe";
	private static final String OTHER_USERNAME = "jane.doe";
	private static final String PASSWORD = "secure";
	private final SimpleHttpSession session;
	private final PageServiceLoginExecutor executor;
	private final AGUser user;
	private final AGUser otherUser;

	public PageServiceLoginExecutorTest() {

		this.session = new SimpleHttpSession("");

		var request = Mockito.mock(IAjaxRequest.class);
		Mockito.when(request.getHttpSession()).thenReturn(session);
		Mockito.when(request.getHttpRequest()).thenReturn(Mockito.mock(HttpServletRequest.class));
		Mockito.when(request.getHttpRequest().getRemoteAddr()).thenReturn("1.2.3.4");
		this.executor = new PageServiceLoginExecutor(request);

		this.user = insertUser("John", "Doe", USERNAME);
		this.otherUser = insertUser("Jane", "Doe", OTHER_USERNAME);
		insertPassword(user, PASSWORD);
		insertPassword(otherUser, PASSWORD);

		AGCoreModuleInstance.getInstance().setEmailServer(insertDummyServer()).save();
	}

	@Test
	public void testExecuteLoginWithoutTwoFactorAuthentication() {

		Optional<OneTimePasswordChallenge> challenge = executor.executeLogin(USERNAME, PASSWORD, 1);

		assertTrue(challenge.isEmpty());
		assertLoggedIn(user);
		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	@Test
	public void testExecuteLoginWithTwoFactorAuthentication() {

		requireTwoFactorAuthentication(user);

		OneTimePasswordChallenge challenge = executor.executeLogin(USERNAME, PASSWORD, 1).get();

		assertSame(user, challenge.getUser());
		assertNotLoggedIn();
		assertNotNull(getOneTimePasswordSentTo(user));
	}

	@Test
	public void testExecuteLoginWithTwoFactorAuthenticationAndWrongPassword() {

		requireTwoFactorAuthentication(user);

		assertException(PageServiceLoginExceptionWrongUsernameOrPassword.class, () -> executor.executeLogin(USERNAME, "wrong", 1));
		assertNotLoggedIn();
		assertEquals(0, AGBufferedEmail.TABLE.countAll());
	}

	@Test
	public void testCompleteLoginWithCorrectOneTimePassword() {

		requireTwoFactorAuthentication(user);
		OneTimePasswordChallenge challenge = executor.executeLogin(USERNAME, PASSWORD, 1).get();

		executor.completeLogin(challenge, getOneTimePasswordSentTo(user));

		assertLoggedIn(user);
	}

	@Test
	public void testCompleteLoginWithWrongOneTimePassword() {

		requireTwoFactorAuthentication(user);
		OneTimePasswordChallenge challenge = executor.executeLogin(USERNAME, PASSWORD, 1).get();

		assertException(PageServiceLoginExceptionWrongOneTimePassword.class, () -> executor.completeLogin(challenge, "wrong"));
		assertNotLoggedIn();

		// the one-time password must be invalidated after the first attempt
		assertException(PageServiceLoginExceptionWrongOneTimePassword.class, () -> executor.completeLogin(challenge, getOneTimePasswordSentTo(user)));
		assertNotLoggedIn();
	}

	@Test
	public void testCompleteLoginWithExpiredOneTimePassword() {

		requireTwoFactorAuthentication(user);
		OneTimePasswordChallenge challenge = executor.executeLogin(USERNAME, PASSWORD, 1).get();
		testClock.add(Duration.ofMinutes(6));

		assertException(PageServiceLoginExceptionWrongOneTimePassword.class, () -> executor.completeLogin(challenge, getOneTimePasswordSentTo(user)));
		assertNotLoggedIn();
	}

	@Test
	public void testCompleteLoginWithOneTimePasswordOfOtherUser() {

		requireTwoFactorAuthentication(user);
		requireTwoFactorAuthentication(otherUser);
		OneTimePasswordChallenge challenge = executor.executeLogin(USERNAME, PASSWORD, 1).get();
		executor.executeLogin(OTHER_USERNAME, PASSWORD, 2).get();

		assertException(
			PageServiceLoginExceptionWrongOneTimePassword.class,
			() -> executor.completeLogin(challenge, getOneTimePasswordSentTo(otherUser)));
		assertNotLoggedIn();
	}

	private void requireTwoFactorAuthentication(AGUser user) {

		var policy = new AGPasswordPolicy()//
			.setName("Two-Factor Policy for " + user.getLoginName())
			.setTwoFactorAuthentication(true)
			.save();
		user.setPasswordPolicy(policy).save();
	}

	private String getOneTimePasswordSentTo(AGUser user) {

		AGBufferedEmail email = assertOne(//
			AGBufferedEmail.TABLE//
				.createSelect()
				.where(AGBufferedEmail.TO.isEqual(user.getEmailAddress()))
				.list());
		String[] lines = email.getContent().split("\r\n");
		return lines[lines.length - 1];
	}

	private void assertLoggedIn(AGUser user) {

		assertSame(user, SofticarAjaxSession.getInstance(session).get().getUser());
		assertSame(user, CurrentUser.get());
	}

	private void assertNotLoggedIn() {

		assertTrue(SofticarAjaxSession.getInstance(session).isEmpty());
	}
}
