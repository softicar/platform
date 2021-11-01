package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineMethods;
import com.softicar.platform.core.module.page.navigation.PageNavigationMarker;
import com.softicar.platform.core.module.page.service.PageServiceDocumentBuilder;
import com.softicar.platform.core.module.start.page.StartPageMarker;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Rule;
import org.junit.Test;

public class PageServiceLoginDivTest extends AbstractDbTest implements IAjaxSeleniumLowLevelTestEngineMethods, CoreModuleTestFixtureMethods {

	private static final String LOGIN_FAILURE_MESSAGE = "Wrong username or password.";
	private static final String LOGIN_PASSWORD = "somePassword";
	private static final String LOGIN_USER = "firstname.lastname";

	@Rule public final IAjaxSeleniumLowLevelTestEngine testEngine;

	public PageServiceLoginDivTest() {

		this.testEngine = new AjaxSeleniumLowLevelTestEngine();
		insertPassword(insertUser("foo", "bar", LOGIN_USER), LOGIN_PASSWORD);
		openTestNode(this::createLoginDiv);
	}

	@Override
	public IAjaxSeleniumLowLevelTestEngine getTestEngine() {

		return testEngine;
	}

	@Test
	public void testWithValidUserAndPasswordAndClickOnLoginButton() {

		send(PageServiceLoginDivMarker.USER_INPUT, LOGIN_USER);
		send(PageServiceLoginDivMarker.PASSWORD_INPUT, LOGIN_PASSWORD);
		click(PageServiceLoginDivMarker.LOGIN_BUTTON);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithValidUserAndPasswordAndEnterOnLoginButton() {

		send(PageServiceLoginDivMarker.USER_INPUT, LOGIN_USER);
		send(PageServiceLoginDivMarker.PASSWORD_INPUT, LOGIN_PASSWORD);
		send(PageServiceLoginDivMarker.LOGIN_BUTTON, Key.ENTER);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithValidUserAndPasswordAndEnterOnUserInput() {

		send(PageServiceLoginDivMarker.USER_INPUT, LOGIN_USER);
		send(PageServiceLoginDivMarker.PASSWORD_INPUT, LOGIN_PASSWORD);
		send(PageServiceLoginDivMarker.USER_INPUT, Key.ENTER);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithValidUserAndPasswordAndEnterOnPasswordInput() {

		send(PageServiceLoginDivMarker.USER_INPUT, LOGIN_USER);
		send(PageServiceLoginDivMarker.PASSWORD_INPUT, LOGIN_PASSWORD);
		send(PageServiceLoginDivMarker.PASSWORD_INPUT, Key.ENTER);
		waitForServer();

		assertLoginSuccess();
	}

	@Test
	public void testWithInvalidUser() {

		send(PageServiceLoginDivMarker.USER_INPUT, "invalid-user");
		send(PageServiceLoginDivMarker.PASSWORD_INPUT, LOGIN_PASSWORD);
		click(PageServiceLoginDivMarker.LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithInvalidPassword() {

		send(PageServiceLoginDivMarker.USER_INPUT, LOGIN_USER);
		send(PageServiceLoginDivMarker.PASSWORD_INPUT, "invalid-password");
		click(PageServiceLoginDivMarker.LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithInvalidUserAndPassword() {

		send(PageServiceLoginDivMarker.USER_INPUT, "invalid-user");
		send(PageServiceLoginDivMarker.PASSWORD_INPUT, "invalid-password");
		click(PageServiceLoginDivMarker.LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithoutUserAndPassword() {

		click(findNodeOrFail(PageServiceLoginDivMarker.LOGIN_BUTTON));
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithoutUser() {

		send(PageServiceLoginDivMarker.PASSWORD_INPUT, LOGIN_PASSWORD);
		click(PageServiceLoginDivMarker.LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithoutPassword() {

		send(PageServiceLoginDivMarker.USER_INPUT, LOGIN_USER);
		click(PageServiceLoginDivMarker.LOGIN_BUTTON);
		waitForServer();

		assertLoginFailure();
	}

	@Test
	public void testWithTabOnUserInput() {

		send(findNodeOrFail(PageServiceLoginDivMarker.USER_INPUT), Key.TAB);

		assertFocused(PageServiceLoginDivMarker.PASSWORD_INPUT);
	}

	@Test
	public void testWithTabOnPasswordInput() {

		send(findNodeOrFail(PageServiceLoginDivMarker.PASSWORD_INPUT), Key.TAB);

		assertFocused(PageServiceLoginDivMarker.LOGIN_BUTTON);
	}

	private PageServiceLoginDiv createLoginDiv(IAjaxDocument document) {

		return new PageServiceLoginDiv(new PageServiceDocumentBuilder(document));
	}

	private void assertLoginFailure() {

		assertNodeWithText(PageServiceLoginDivMarker.ERROR_MESSAGE_ELEMENT, LOGIN_FAILURE_MESSAGE);
		assertNoModalDialog();
	}

	private void assertLoginSuccess() {

		findNodeOrFail(PageNavigationMarker.PAGE_CONTENT_DIV);
		findNodeOrFail(StartPageMarker.MAIN_ELEMENT);
		assertNoModalDialog();
	}
}
