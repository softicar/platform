package com.softicar.platform.emf.test.simple;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Set;
import java.util.TreeSet;

public class EmfTestObject extends EmfTestObjectGenerated implements IEmfObject<EmfTestObject> {

	private final Set<IBasicUser> authorizedUsers = new TreeSet<>();

	@Override
	public IDisplayString toDisplayWithoutId() {

		return new DisplayString().append(getName());
	}

	public void addAuthorizedUser(IBasicUser user) {

		this.authorizedUsers.add(user);
	}

	public boolean isAuthorizedUser(IBasicUser user) {

		return authorizedUsers.contains(user);
	}
}
