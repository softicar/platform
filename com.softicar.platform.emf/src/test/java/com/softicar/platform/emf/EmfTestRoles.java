package com.softicar.platform.emf;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.authorization.role.EmfRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public interface EmfTestRoles {

	IEmfRole<EmfTestObject> AUTHORIZED_USER = new EmfRole<>(//
		IDisplayString.create("Authorized User"),
		EmfTestObject::isAuthorizedUser);
}
