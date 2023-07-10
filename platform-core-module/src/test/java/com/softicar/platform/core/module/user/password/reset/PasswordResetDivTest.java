package com.softicar.platform.core.module.user.password.reset;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.testing.selenium.engine.level.high.AjaxSeleniumTestExecutionEngine;
import com.softicar.platform.common.security.crypt.Bcrypt;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreTestMarker;
import com.softicar.platform.core.module.page.service.PageServiceDocumentBuilder;
import com.softicar.platform.core.module.test.fixture.CoreModuleTestFixtureMethods;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import org.junit.Rule;
import org.junit.Test;

public class PasswordResetDivTest extends AbstractDbTest implements CoreModuleTestFixtureMethods, IDomTestExecutionEngineMethods {

	@Rule public final AjaxSeleniumTestExecutionEngine engine = new AjaxSeleniumTestExecutionEngine();

	private static final String LOGIN_USER = "firstname.lastname";
	private final AGUser user;

	public PasswordResetDivTest() {

		this.user = insertUser("foo", "bar", LOGIN_USER);
		insertPassword(user, "old-password");
		setNodeSupplier(() -> createPasswordResetDiv());
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testDisplayErrorWithoutParameters() {

		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_DIV);
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT).assertContainsText(CoreI18n.NO_PASSWORD_RESET_REQUEST_FOUND);
	}

	@Test
	public void testDisplayPasswordResetFormWithParameters() {

		setupAndAssertPasswordReset();
		//test error with empty inputs
		findButton(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CHANGE_PASSWORD_BUTTON).click();
		assertPasswordCriterionDisplayed();

		//test error with bad passwords
		findInput(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_NEW_PASSWORD_INPUT).setInputValue("password");
		findInput(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CONFIRM_PASSWORD_INPUT).setInputValue("password");
		findButton(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CHANGE_PASSWORD_BUTTON).click();
		assertPasswordCriterionDisplayed();
		assertFalse(Bcrypt.verifyPassword("password", user.getActivePassword().getEncryptedPassword()));

		//test error with mismatching inputs
		findInput(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_NEW_PASSWORD_INPUT).setInputValue("New-password1");
		findInput(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CONFIRM_PASSWORD_INPUT).setInputValue("New-password2");
		findButton(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CHANGE_PASSWORD_BUTTON).click();
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT).assertContainsText(CoreI18n.THE_ENTERED_PASSWORDS_DO_NOT_MATCH);
		assertFalse(Bcrypt.verifyPassword("New-password1", user.getActivePassword().getEncryptedPassword()));

		//test password changed with valid input
		findInput(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CONFIRM_PASSWORD_INPUT).setInputValue("New-password1");
		findButton(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CHANGE_PASSWORD_BUTTON).click();
		assertTrue(Bcrypt.verifyPassword("New-password1", user.getActivePassword().getEncryptedPassword()));
		assertFalse(AGUserPasswordResetRequest.TABLE.loadAll().get(0).isActive());
	}

	private void setupAndAssertPasswordReset() {

		new AGUserPasswordResetRequest().setUser(user).setUuid("aa-bb-cc-dd").save();
		engine.setUrlParameter("passwordReset", "aa-bb-cc-dd");
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_DIV).assertDisplayed();
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_FORM).assertDisplayed();
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT).assertContainsNoText();
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_NEW_PASSWORD_INPUT).assertDisplayed();
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CONFIRM_PASSWORD_INPUT).assertDisplayed();
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_CHANGE_PASSWORD_BUTTON).assertDisplayed();
	}

	private void assertPasswordCriterionDisplayed() {

		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT)
			.assertContainsText(CoreI18n.THE_PASSWORD_HAS_A_MINIMUM_LENGTH_OF_ARG1_CHARACTERS.toDisplay(10));
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT)
			.assertContainsText(CoreI18n.THE_PASSWORD_CONTAINS_AT_LEAST_ARG1_DISTINCT_CHARACTERS.toDisplay(7));
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT).assertContainsText(CoreI18n.THE_PASSWORD_CONTAINS_ONLY_ASCII_CHARACTERS);
		findNode(CoreTestMarker.PAGE_SERVICE_PASSWORD_RESET_ERROR_ELEMENT)
			.assertContainsText(CoreI18n.THE_PASSWORD_FULFILLS_AT_LEAST_ARG1_SUB_CRITERIA.toDisplay(3));
	}

	private PasswordResetDiv createPasswordResetDiv() {

		var document = AjaxDocument.getCurrentDocument().get();
		return new PasswordResetDiv(new PageServiceDocumentBuilder(document), document);
	}
}
