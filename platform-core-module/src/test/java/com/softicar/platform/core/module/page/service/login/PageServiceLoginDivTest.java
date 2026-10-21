package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.IAjaxSeleniumLowLevelTestEngineMethods;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.page.service.PageServiceDocumentBuilder;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.password.policy.AGPasswordPolicy;
import com.softicar.platform.core.module.user.password.reset.AGUserPasswordResetRequest;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.dom.DomTestMarker;
import org.junit.Rule;
import org.junit.Test;

public class PageServiceLoginDivTest extends AbstractDbTest implements IAjaxSeleniumLowLevelTestEngineMethods, CoreModuleTestFixtureMethods {

	private static final String LOGIN_FAILURE_MESSAGE = "Wrong username or password.";
	private static final String LOGIN_PASSWORD = "somePassword";
	private static final String LOGIN_USER = "firstname.lastname";

	@Rule public final AjaxSeleniumLowLevelTestEngine testEngine;
	private final AGUser user;

	public PageServiceLoginDivTest() {

		this.testEngine = new AjaxSeleniumLowLevelTestEngine();
		AGCoreModuleInstance
			.getInstance()//
			.setEmailServer(insertDummyServer())
			.setPasswordResetFunctionality(true)
			.save();
		this.user = insertUser("foo", "bar", LOGIN_USER);
		insertPassword(user, LOGIN_PASSWORD);
		openTestNode(this::createLoginDiv);
	}

	@Override
	public AjaxSeleniumLowLevelTestEngine getTestEngine() {

		return testEngine;
	}

	@Test
	public void testWithoutInteraction() {

		assertLoginFormDisplayed();
	}

	@Test
	public void testWithValidUserAndPasswordAndClickOnLoginButton() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithValidUserAndPasswordAndEnterOnLoginButton() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON, Key.ENTER);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithValidUserAndPasswordAndEnterOnUserInput() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, Key.ENTER);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithValidUserAndPasswordAndEnterOnPasswordInput() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, Key.ENTER);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithInvalidUser() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, "invalid-user");
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithInvalidPassword() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, "invalid-password");
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithInvalidUserAndPassword() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, "invalid-user");
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, "invalid-password");
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithoutUserAndPassword() {

		click(findNodeOrFail(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON));
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithoutUser() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithoutPassword() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithTwoFactorAuthentication() {

		requireTwoFactorAuthentication();

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertOneTimePasswordFormDisplayed();

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_ONE_TIME_PASSWORD_INPUT, getOneTimePassword());
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithTwoFactorAuthenticationAndEnterOnOneTimePasswordInput() {

		requireTwoFactorAuthentication();

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, Key.ENTER);
		waitForServer();

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_ONE_TIME_PASSWORD_INPUT, getOneTimePassword());
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_ONE_TIME_PASSWORD_INPUT, Key.ENTER);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithTwoFactorAuthenticationAndWrongOneTimePassword() {

		requireTwoFactorAuthentication();

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT, LOGIN_USER);
		send(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT, LOGIN_PASSWORD);
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_ONE_TIME_PASSWORD_INPUT, "wrong");
		click(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
		waitForServer();

		assertLoginFormDisplayed();
		assertNodeWithText(CoreTestMarker.PAGE_SERVICE_LOGIN_ERROR_MESSAGE_ELEMENT, CoreI18n.WRONG_OR_EXPIRED_ONE_TIME_PASSWORD.toString());
	}

	@Test
	public void testWithTabOnUserInput() {

		send(findNodeOrFail(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT), Key.TAB);

		assertFocused(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT);
	}

	@Test
	public void testWithTabOnPasswordInput() {

		send(findNodeOrFail(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT), Key.TAB);

		assertFocused(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
	}

	@Test
	public void testCreateResetPasswordRequestForExistingUser() {

		assertEquals(0, AGUserPasswordResetRequest.TABLE.countAll());

		click(CoreTestMarker.PAGE_SERVICE_LOGIN_RESET_PASSWORD_BUTTON);
		waitForServer();

		send(findModalPromptOrFail().getInputElement(), LOGIN_USER);
		click(findModalPromptOrFail().getOkayButton());
		waitForServer();

		assertModalAlertWithText(CoreI18n.PASSWORD_RESET_REQUEST_HAS_BEEN_CREATED);
		click(findModalAlertOrFail().getCloseButton());

		assertEquals(1, AGUserPasswordResetRequest.TABLE.countAll());
	}

	@Test
	public void testCreateResetPasswordRequestForNonExistingUser() {

		send(CoreTestMarker.PAGE_SERVICE_LOGIN_RESET_PASSWORD_BUTTON, Key.ENTER);
		waitForServer();

		send(findModalPromptOrFail().getInputElement(), "invalid-user");
		send(findModalPromptOrFail().getOkayButton(), Key.ENTER);
		waitForServer();

		assertModalAlertWithText(CoreI18n.COULD_NOT_RESET_USER_PASSWORD);
	}

	private PageServiceLoginDiv createLoginDiv(IAjaxDocument document) {

		return new PageServiceLoginDiv(new PageServiceDocumentBuilder(document));
	}

	private void assertLoginFailure() {

		assertNodeWithText(CoreTestMarker.PAGE_SERVICE_LOGIN_ERROR_MESSAGE_ELEMENT, LOGIN_FAILURE_MESSAGE);
		assertNoModalDialog();
	}

	private void assertLoginSuccess() {

		findNodeOrFail(CoreTestMarker.PAGE_NAVIGATION_PAGE_CONTENT_DIV);
		findNodeOrFail(CoreTestMarker.START_PAGE_MAIN_ELEMENT);
		assertNoModalDialog();
	}

	private void assertLoginFormDisplayed() {

		findNodeOrFail(CoreTestMarker.PAGE_SERVICE_LOGIN_FORM);
		assertNoNode(CoreTestMarker.PAGE_NAVIGATION_PAGE_CONTENT_DIV);
		assertNoNode(CoreTestMarker.START_PAGE_MAIN_ELEMENT);
		assertNoModalDialog();
	}

	private void assertOneTimePasswordFormDisplayed() {

		findNodeOrFail(CoreTestMarker.PAGE_SERVICE_LOGIN_ONE_TIME_PASSWORD_FORM);
		assertNoNode(CoreTestMarker.PAGE_SERVICE_LOGIN_FORM);
		assertNoNode(CoreTestMarker.PAGE_NAVIGATION_PAGE_CONTENT_DIV);
		assertNoModalDialog();
	}

	private void requireTwoFactorAuthentication() {

		var policy = new AGPasswordPolicy()//
			.setName("Two-Factor Policy")
			.setTwoFactorAuthentication(true)
			.save();
		user.setPasswordPolicy(policy).save();
	}

	private String getOneTimePassword() {

		String[] lines = assertOne(AGBufferedEmail.TABLE.loadAll()).getContent().split("\r\n");
		return lines[lines.length - 1];
	}

	private void assertModalAlertWithText(IDisplayString text) {

		findModalAlertOrFail();
		assertNodeWithText(DomTestMarker.MODAL_DIALOG_CONTENT, text.toString());
	}
}
