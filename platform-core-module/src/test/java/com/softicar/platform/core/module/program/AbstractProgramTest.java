package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.test.AbstractDbTest;

public abstract class AbstractProgramTest extends AbstractDbTest {

	protected final AGUser user;

	public AbstractProgramTest() {

		this.user = insertUser();
	}

	private AGUser insertUser() {

		return new AGUser()//
			.setActive(false)
			.setLoginName("john.doe")
			.setFirstName("John")
			.setLastName("Doe")
			.setEmailAddress("john.doe@example.com")
			.save();
	}
}
