package com.softicar.platform.emf.authorizer;

import com.softicar.platform.emf.EmfI18n;

public class EmfTableRowNotVisibleToUserException extends EmfAccessPermissionException {

	public EmfTableRowNotVisibleToUserException() {

		super(EmfI18n.ACCESS_TO_THIS_ENTRY_IS_RESTRICTED);
	}
}
