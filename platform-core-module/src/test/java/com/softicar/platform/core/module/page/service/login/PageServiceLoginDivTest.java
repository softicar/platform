package com.softicar.platform.core.module.page.service.login;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngine;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineMethods;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.service.PageServiceDocumentBuilder;
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
	public void testWithTabOnUserInput() {

		send(findNodeOrFail(CoreTestMarker.PAGE_SERVICE_LOGIN_USER_INPUT), Key.TAB);

		assertFocused(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT);
	}

	@Test
	public void testWithTabOnPasswordInput() {

		send(findNodeOrFail(CoreTestMarker.PAGE_SERVICE_LOGIN_PASSWORD_INPUT), Key.TAB);

		assertFocused(CoreTestMarker.PAGE_SERVICE_LOGIN_LOGIN_BUTTON);
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
}
