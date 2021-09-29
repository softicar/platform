package com.softicar.platform.emf.authorizer;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;

public class EmfAccessPermissionException extends SofticarUserException {

	public EmfAccessPermissionException(IDisplayString message) {

		super(message);
	}
}
