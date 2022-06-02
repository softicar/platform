package com.softicar.platform.emf.test.simple;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.test.simple.authorization.EmfTestObjectAuthorizedUser;
import com.softicar.platform.emf.test.user.EmfTestUser;

public class EmfTestObject extends EmfTestObjectGenerated implements IEmfObject<EmfTestObject> {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getName());
	}

	public void addAuthorizedUser(EmfTestUser user) {

		new EmfTestObjectAuthorizedUser().setObject(this).setUser(user).save();
	}

	public boolean isAuthorizedUser(IBasicUser user) {

		return EmfTestObjectAuthorizedUser.TABLE//
			.createSelect()
			.where(EmfTestObjectAuthorizedUser.OBJECT.equal(this))
			.where(EmfTestObjectAuthorizedUser.USER.isEqualId(user.getId()))
			.exists();
	}
}
