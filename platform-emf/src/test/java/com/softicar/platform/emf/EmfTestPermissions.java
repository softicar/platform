package com.softicar.platform.emf;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.permission.EmfPermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public interface EmfTestPermissions {

	IEmfPermission<EmfTestObject> AUTHORIZED_USER = new EmfPermission<>(//
		IDisplayString.create("Authorized User"),
		EmfTestObject::isAuthorizedUser);
}
