package com.softicar.platform.core.module.user.password.reset;

import com.softicar.platform.ajax.document.IAjaxDocumentParameters;
import java.util.Objects;
import java.util.Optional;

public class PasswordResetRequestParameterParser {

	private static final String PASSWORD_RESET_PARAMETER = "passwordReset";
	private final IAjaxDocumentParameters parameters;

	public PasswordResetRequestParameterParser(IAjaxDocumentParameters parameters) {

		this.parameters = Objects.requireNonNull(parameters);
	}

	public static String getPasswordResetParameter() {

		return PASSWORD_RESET_PARAMETER;
	}

	/**
	 * This method tries to parse the "passwordReset" parameter from the Url.
	 *
	 * @return the optional {@link AGUserPasswordResetRequest}
	 */
	public Optional<AGUserPasswordResetRequest> getPasswordResetRequest() {

		return AGUserPasswordResetRequest.loadByUuidAsOptional(parameters.getParameterOrNull(PASSWORD_RESET_PARAMETER));
	}
}
