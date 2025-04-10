package com.softicar.platform.core.module.ajax.login;

import com.softicar.platform.ajax.simple.SimpleHttpSession;
import com.softicar.platform.ajax.simple.SimpleServletOutputStream;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.email.buffer.AGBufferedEmail;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.core.module.user.AGUser;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.mockito.Mockito;

public class SofticarAjaxTwoFactorAuthenticatorTest extends AbstractCoreTest {

	private static final String USER_EMAIL_ADDRESS = "user@example.com";
	private final AGUser user;
	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final SimpleServletOutputStream responseOutputStream;
	private final SimpleHttpSession session;
	private final SofticarAjaxTwoFactorAuthenticator authenticator;

	public SofticarAjaxTwoFactorAuthenticatorTest() throws IOException {

		this.user = insertTestUser().setEmailAddress(USER_EMAIL_ADDRESS).save();
		this.request = Mockito.mock(HttpServletRequest.class);
		this.response = Mockito.mock(HttpServletResponse.class);
		this.responseOutputStream = new SimpleServletOutputStream();
		this.session = new SimpleHttpSession("");
		this.authenticator = new SofticarAjaxTwoFactorAuthenticator(user, request, response);

		AGCoreModuleInstance.getInstance().setEmailServer(insertDummyServer()).save();
		setupMocking();
	}

	@SuppressWarnings("resource")
	private void setupMocking() throws IOException {

		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(response.getOutputStream()).thenReturn(responseOutputStream);
	}

	@Test
	public void testInitiateTwoFactorAuthentication() {

		authenticator.initiateTwoFactorAuthentication();

		OneTimePassword password = getSessionAttribute().peekPassword();
		assertNotNull(password);
		assertEquals(1, password.getIndex());

		AGBufferedEmail email = Asserts.assertOne(AGBufferedEmail.TABLE.loadAll());
		assertEquals(AGCoreModuleInstance.getInstance().getEmailServer(), email.getEmailServer());
		assertEquals("One-Time Password", email.getSubject());
		assertEquals(USER_EMAIL_ADDRESS, email.getTo());
		Asserts.assertContains(password.getText(), email.getContent());
	}

	@Test
	public void testValidatePasswordWithCorrectPassword() {

		OneTimePassword password = getSessionAttribute().generateAndStorePasswort();

		Mockito//
			.when(request.getParameter(SofticarAjaxTwoFactorAuthenticator.ONE_TIME_PASSWORD_REQUEST_PARAMETER))
			.thenReturn(password.getText());

		boolean result = authenticator.validateOneTimePassword();

		assertTrue(result);
		assertNull(getSessionAttribute().peekPassword());
	}

	@Test
	public void testValidatePasswordWithIncorrectPassword() {

		getSessionAttribute().generateAndStorePasswort();

		Mockito//
			.when(request.getParameter(SofticarAjaxTwoFactorAuthenticator.ONE_TIME_PASSWORD_REQUEST_PARAMETER))
			.thenReturn("xxx");

		boolean result = authenticator.validateOneTimePassword();

		assertFalse(result);
		assertNull(getSessionAttribute().peekPassword());
	}

	private OneTimePasswordSessionAttribute getSessionAttribute() {

		return OneTimePasswordSessionAttribute.getInstance(session);
	}
}
